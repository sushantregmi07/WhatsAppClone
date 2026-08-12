package com.example.whatsappclone.domain.model

/**
 * Domain-safe avatar identifier. The UI layer maps each key to
 * `R.drawable.avatar_<snake_case_name>` — no Android resource IDs leak here.
 */
enum class AvatarKey(val drawableName: String) {
    MARTIN_RANDOLPH("avatar_martin_randolph"),
    ELENA_MORALES("avatar_elena_morales"),
    KAREN_CASTILLO("avatar_karen_castillo"),
    DANIEL_ABRAMOV("avatar_daniel_abramov"),
    MARTHA_CRAIG("avatar_martha_craig"),
    TABITHA_POTTER("avatar_tabitha_potter"),
    PRIYA_SHARMA("avatar_priya_sharma"),
    JAMES_THORNTON("avatar_james_thornton"),
}
