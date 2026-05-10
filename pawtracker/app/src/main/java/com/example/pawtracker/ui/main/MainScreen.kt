package com.example.pawtracker.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import com.example.pawtracker.R
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pawtracker.ui.navigation.NavigationType
import com.example.pawtracker.ui.theme.LocalSpacing
/**
 * First‑time welcome screen shown only when the user opens the app for the first time.
 * After onboarding is completed, the app starts directly at the Statistics screen.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onContinueClick: () -> Unit,
    innerPadding: PaddingValues,
    navigationType: NavigationType
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
        ) {

        Image(
            painter = painterResource(id = R.drawable.background_mainscreen),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .padding(horizontal = spacing.large),
                verticalArrangement = if (navigationType == NavigationType.BOTTOM_NAVIGATION) Arrangement.Top else Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                    Spacer(modifier = Modifier.height(100.dp))
                }

                Text(
                    text = uiState.welcomeText,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = if (navigationType == NavigationType.BOTTOM_NAVIGATION)
                        TextAlign.Start else TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(spacing.medium))

                Text(
                    text = uiState.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = if (navigationType == NavigationType.BOTTOM_NAVIGATION)
                        TextAlign.Start else TextAlign.Center
                )
                if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.height(spacing.extraLarge))
                }


                Button(
                    onClick = onContinueClick,
                    modifier = Modifier.fillMaxWidth(0.6f)
                        .fillMaxWidth(if (navigationType == NavigationType.BOTTOM_NAVIGATION) 0.8f else 0.4f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(R.string.main_continue_button))
                }
            }
        }
    }

