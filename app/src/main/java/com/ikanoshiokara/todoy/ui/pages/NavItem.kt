package com.ikanoshiokara.todoy.ui.pages

sealed class NavItem(
    val name: String
) {
    object MainPage: NavItem("main")
}
