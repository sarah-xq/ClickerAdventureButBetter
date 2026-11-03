package com.example.idlealchemist.model

/**
 * Describes one stage displayed on the Crucible Trail.
 *
 * This milestone displays challenge definitions without tracking
 * completion, granting rewards, or restricting access to later stages.
 *
 * @property number Unique positive stage number used as its list identity.
 * @property title Display name of the stage.
 * @property element Element associated with the challenge.
 * @property objective Instructions describing the challenge.
 * @property reward Reward associated with future stage completion.
 */
data class TrailStage(
    val number: Int,
    val title: String,
    val element: String,
    val objective: String,
    val reward: String,
)