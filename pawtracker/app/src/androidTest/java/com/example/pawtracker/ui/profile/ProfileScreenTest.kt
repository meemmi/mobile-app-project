package com.example.pawtracker.ui.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.pawtracker.data.local.DogProfileEntity
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createFakeVM(profile: DogProfileEntity): ProfileViewModel {
        val fakeRepo = FakeDogProfileRepository(profile)
        return ProfileViewModel(fakeRepo)
    }

    private fun fakeProfile() = DogProfileEntity(
        id = 0,
        imageUri = "",
        name = "Buddy",
        breed = "Golden Retriever",
        age = "5",
        height = "60",
        weight = "30",
        dailyDurationGoal = 45L,
        dailyDistanceGoal = 3.5f,
        weeklyDurationGoal = 300L,
        weeklyDistanceGoal = 20f
    )

    @Test
    fun profile_name_is_displayed() {
        val vm = createFakeVM(fakeProfile())

        composeTestRule.setContent {
            ProfileScreen(
                innerPadding = PaddingValues(),
                viewModel = vm,
                onNavigateToEdit = {}
            )
        }

        composeTestRule.onNodeWithTag("profile_name")
            .assertTextContains("Buddy")
    }
}
