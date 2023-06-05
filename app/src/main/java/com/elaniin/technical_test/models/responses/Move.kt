package com.elaniin.technical_test.models.responses

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)