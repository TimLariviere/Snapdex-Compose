package com.kanoyatech.snapdex.ui.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.designsystem.AvatarView
import com.kanoyatech.snapdex.theme.designsystem.LinkButton
import com.kanoyatech.snapdex.theme.designsystem.PrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SecondaryButton
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
    Box {
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
                    avatarId = state.user.avatarId,
                    isSelected = false,
                    modifier = Modifier
                        .height(48.dp)
                )

                Column {
                    Text(
                        text = state.user.name,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = state.user.email,
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

            Column {
                LinkButton(
                    text = "Delete account",
                    color = MaterialTheme.colorScheme.error
                ) {
                    onAction(ProfileAction.OnDeleteAccountClick)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        if (state.showAccountDeletionDialog) {
            Dialog(
                onDismissRequest = {}
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Account deletion",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Text(
                        text = "You are about to permanently delete the account with all associated data.\nDo you want to continue?",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        SecondaryButton(
                            text = "Delete",
                            isDestructive = true,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            onAction(ProfileAction.OnAccountDeletionConfirm)
                        }

                        PrimaryButton(
                            text = "Cancel",
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            onAction(ProfileAction.OnAccountDeletionCancel)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            paddingValues = PaddingValues(0.dp),
            state = ProfileState(
                user = User(
                    id = "",
                    avatarId = 4,
                    name = "Roger",
                    email = "roger@snapdex.com"
                )
            ),
            onAction = {}
        )
    }
}