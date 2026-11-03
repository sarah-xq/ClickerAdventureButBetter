package com.example.asgn3_clickeradventure.data

import com.example.asgn3_clickeradventure.model.ActivePotion
import com.example.asgn3_clickeradventure.model.Achievements
import com.example.asgn3_clickeradventure.model.GameState
import com.example.asgn3_clickeradventure.model.Potions
import com.example.asgn3_clickeradventure.model.withUnlocks
import kotlin.math.roundToInt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * The single source of truth for the game, shared by the clicker, achievements and shop screens.
 * Screens never change the state themselves; they call these functions.
 * The upgrade cost formulas are the ones I wrote originally.
 */
class GameRepository {
    private val growthFactorExtra = 1.15
    private val growthFactorAuto = 1.55

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    /** A manual tap on the cauldron. */
    fun tap() = _state.update { s ->
        val gain = s.clickGain
        s.copy(
            points = s.points + gain,
            stats = s.stats.copy(
                manualClicks = s.stats.manualClicks + 1,
                brewed = s.stats.brewed + gain
            )
        ).withUnlocks()
    }

    fun buyExtraClick() = _state.update { s ->
        if (s.points < s.extraClickCost) s else s.copy(
            points = s.points - s.extraClickCost,
            extraClickCost = s.extraClickCost + (s.extraClickCost * growthFactorExtra).roundToInt(),
            clickPower = s.clickPower + 1,
            stats = s.stats.copy(extraClicksBought = s.stats.extraClicksBought + 1)
        ).withUnlocks()
    }

    fun buyApprentice() = _state.update { s ->
        if (s.points < s.autoCost) s else s.copy(
            points = s.points - s.autoCost,
            autoCost = s.autoCost + (s.autoCost * growthFactorAuto).roundToInt(),
            apprenticeCount = s.apprenticeCount + 1,
            stats = s.stats.copy(apprenticesHired = s.stats.apprenticesHired + 1)
        ).withUnlocks()
    }

    /**
     * Activates a potion. Called once the (mock) payment has gone through, so it costs no points.
     * Buying a potion that is already active adds its duration to the time left, so 15s left
     * plus a 30s potion becomes 45s.
     */
    fun buyPotion(id: String) = _state.update { s ->
        val potion = Potions.byId(id)
        if (potion == null) s else {
            val timeLeft = s.activePotions.firstOrNull { it.potionId == id }?.secondsLeft ?: 0
            s.copy(
                activePotions = s.activePotions.filterNot { it.potionId == id } +
                        ActivePotion(id, timeLeft + potion.durationSeconds),
                stats = s.stats.copy(potionsUsed = s.stats.potionsUsed + 1)
            ).withUnlocks()
        }
    }

    /** Called once per second: apprentices brew, potion and message timers count down. */
    fun tick() = _state.update { s ->
        val auto = s.autoGain
        s.copy(
            points = s.points + auto,
            stats = s.stats.copy(brewed = s.stats.brewed + auto),
            activePotions = s.activePotions
                .map { it.copy(secondsLeft = it.secondsLeft - 1) }
                .filter { it.secondsLeft > 0 },
            unlockNotice = s.unlockNotice?.let {
                if (it.secondsLeft <= 1) null else it.copy(secondsLeft = it.secondsLeft - 1)
            }
        ).withUnlocks()
    }

    /** Removes an owned achievement. Progress restarts from zero, so it can be earned again. */
    fun deleteAchievement(id: String) = _state.update { s ->
        val achievement = Achievements.all.firstOrNull { it.id == id }
        if (achievement == null || id !in s.ownedAchievements) s else s.copy(
            ownedAchievements = s.ownedAchievements - id,
            baselines = s.baselines + (id to achievement.requirement.stat(s.stats))
        )
    }
}
