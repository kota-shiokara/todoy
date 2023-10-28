package jp.ikanoshiokara.todoy.ui

import com.kiwi.navigationcompose.typed.Destination
import kotlinx.serialization.Serializable

sealed interface NavItem : Destination {
    @Serializable
    data object Home : NavItem
}
