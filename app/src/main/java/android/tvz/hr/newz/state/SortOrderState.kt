package android.tvz.hr.newz.state

sealed interface SortOrderState{
    object Ascending : SortOrderState
    object Descening : SortOrderState
}