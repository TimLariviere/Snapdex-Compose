package com.kanoyatech.snapdex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import com.kanoyatech.snapdex.domain.models.PasswordValidationState
import com.kanoyatech.snapdex.ui.R

@Composable
fun PasswordRequirements(state: PasswordValidationState, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        PasswordRequirement(
            text = stringResource(id = R.string.at_least_x_characters),
            isValid = state.hasMinLength,
        )
        PasswordRequirement(
            text = stringResource(id = R.string.at_least_x_digits),
            isValid = state.hasDigit,
        )
        PasswordRequirement(
            text = stringResource(id = R.string.contains_lowercase),
            isValid = state.hasLowercase,
        )
        PasswordRequirement(
            text = stringResource(id = R.string.contains_uppercase),
            isValid = state.hasUppercase,
        )
    }
}

@Composable
private fun PasswordRequirement(text: String, isValid: Boolean) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (isValid) Icons.Check else Icons.Close,
            contentDescription = null,
            tint =
                if (isValid) {
                    SnapdexTheme.colorScheme.success
                } else {
                    SnapdexTheme.colorScheme.error
                },
            modifier = Modifier.size(20.dp),
        )

        Text(text = text)
    }
}

@Preview
@Composable
private fun PasswordRequirementsPreview() {
    AppTheme { PasswordRequirements(state = PasswordValidationState()) }
}
