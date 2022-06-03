package com.ikanoshiokara.todoy.components.pages

sealed class NavItem(
    val name: String
) {
    object MainPage: NavItem("main")
}
