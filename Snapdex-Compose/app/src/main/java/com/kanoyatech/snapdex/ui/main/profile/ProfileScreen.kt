package com.kanoyatech.snapdex.ui.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.designsystem.AvatarView
import com.kanoyatech.snapdex.theme.designsystem.PrimaryButton
import com.kanoyatech.snapdex.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreenRoot(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = koinViewModel(),
    onLoggedOut: () -> Unit
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ProfileEvent.LoggedOut -> onLoggedOut()
        }
    }

    ProfileScreen(
        paddingValues = paddingValues,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ProfileScreen(
    paddingValues: PaddingValues,
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarView(
                avatarId = state.avatarId,
                isSelected = false,
                modifier = Modifier
                    .height(48.dp)
            )

            Column {
                Text(
                    text = state.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = state.email,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { onAction(ProfileAction.OnLogoutClick) }) {
                Icon(
                    imageVector = Icons.Logout,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            paddingValues = PaddingValues(0.dp),
            state = ProfileState(
                avatarId = 7,
                name = "Test Name",
                email = "test@example.com"
            ),
            onAction = {}
        )
    }
}