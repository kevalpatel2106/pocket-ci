package com.kevalpatel2106.registration.register

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.component.LoadingButton
import com.kevalpatel2106.core.ui.resource.Spacing.GUTTER
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_HUGE
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_LARGE
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_MICRO
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_REGULAR
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_XLARGE
import com.kevalpatel2106.entity.Url

@Composable
internal fun RegisterScreen(viewModel: RegisterViewModel = viewModel()) {
    val state by viewModel.viewState.collectAsState()
    val domainState = rememberSaveable { mutableStateOf(state.defaultUrl.value) }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = GUTTER)
            .verticalScroll(state = scrollState),
    ) {
        CIInfoView(
            ciName = state.ciName,
            ciIcon = state.ciIcon,
            infoUrl = state.ciInfoUrl,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SPACING_LARGE),
        )
        Spacer(modifier = Modifier.padding(top = SPACING_XLARGE))
        DomainTextField(
            value = domainState.value,
            defaultUrl = state.defaultUrl,
            allowUrlEdit = state.allowUrlEdit,
            urlErrorMsg = state.urlErrorMsg,
        ) {
            viewModel.onUrlChange()
            domainState.value = it
        }
        Spacer(modifier = Modifier.padding(top = SPACING_REGULAR))
        PasswordTextField(
            value = passwordState.value,
            sampleAuthToken = state.sampleAuthToken,
            tokenErrorMsg = state.tokenErrorMsg,
            onValueChange = {
                viewModel.onTokenChange()
                passwordState.value = it
            },
        ) {
            viewModel.submit(inputUrl = domainState.value, inputToken = passwordState.value)
        }
        Spacer(modifier = Modifier.padding(top = SPACING_SMALL))
        AuthTokenExplainerText(state.authTokenHintLink)
        Spacer(modifier = Modifier.padding(top = SPACING_LARGE))
        LoadingButton(
            enabled = state.enableAddAccountBtn,
            isLoading = !state.enableAddAccountBtn,
            onClick = {
                viewModel.submit(inputUrl = domainState.value, inputToken = passwordState.value)
            },
        ) { Text(text = stringResource(id = R.string.register_add_account_button_title)) }
        Spacer(modifier = Modifier.padding(top = SPACING_LARGE))
    }
}

@Composable
private fun AuthTokenExplainerText(authTokenHintLink: String) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = stringResource(id = R.string.register_hint_how_to_get_auth_token),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelMedium,
        color = Color.Blue,
        modifier = Modifier
            .clickable { uriHandler.openUri(authTokenHintLink) },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DomainTextField(
    value: String,
    defaultUrl: Url,
    allowUrlEdit: Boolean,
    @StringRes urlErrorMsg: Int?,
    onValueChange: (String) -> Unit,
) = TextField(
    value = value,
    onValueChange = { onValueChange(it) },
    readOnly = !allowUrlEdit,
    textStyle = MaterialTheme.typography.bodyMedium,
    isError = urlErrorMsg != null,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    maxLines = 1,
    singleLine = true,
    leadingIcon = {
        Icon(
            imageVector = Icons.Filled.Link,
            contentDescription = stringResource(id = R.string.register_url_header),
        )
    },
    label = { LabelText(R.string.register_url_header) },
    placeholder = { PlaceHolderText(defaultUrl.value) },
    supportingText = { if (urlErrorMsg != null) SupportingErrorText(urlErrorMsg) },
    shape = TextFieldDefaults.outlinedShape,
    modifier = Modifier.fillMaxWidth(),
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PasswordTextField(
    value: String,
    sampleAuthToken: String,
    @StringRes tokenErrorMsg: Int?,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        isError = tokenErrorMsg != null,
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        singleLine = true,
        supportingText = { if (tokenErrorMsg != null) SupportingErrorText(tokenErrorMsg) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Password,
                contentDescription = stringResource(id = R.string.register_auth_token_header),
            )
        },
        maxLines = 1,
        label = { LabelText(R.string.register_auth_token_header) },
        placeholder = { PlaceHolderText(sampleAuthToken) },
        shape = TextFieldDefaults.outlinedShape,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            PasswordVisibilityToggle(passwordVisible) { passwordVisible = !passwordVisible }
        },
    )
}

@Composable
private fun PasswordVisibilityToggle(
    passwordVisible: Boolean,
    onClick: () -> Unit
) {
    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    val description = if (passwordVisible) R.string.hide_password else R.string.show_password
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = description),
        )
    }
}

@Composable
private fun PlaceHolderText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f),
    )
}

@Composable
private fun LabelText(@StringRes text: Int) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.labelSmall,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.padding(bottom = SPACING_MICRO),
    )
}

@Composable
private fun SupportingErrorText(@StringRes text: Int) = Text(
    text = stringResource(id = text),
    style = MaterialTheme.typography.labelSmall,
    overflow = TextOverflow.Ellipsis,
    maxLines = 2,
    color = Color.Red,
    textAlign = TextAlign.End,
    modifier = Modifier.fillMaxWidth(),
)

@Composable
private fun CIInfoView(
    @StringRes ciName: Int,
    @DrawableRes ciIcon: Int,
    infoUrl: Url,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = ciIcon),
            contentDescription = stringResource(id = ciName),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(SPACING_HUGE)
                .height(SPACING_HUGE)
                .clip(CircleShape),
        )
        Text(
            text = stringResource(id = ciName),
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SPACING_SMALL),
        )
        Text(
            text = infoUrl.value,
            style = MaterialTheme.typography.labelMedium,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            modifier = Modifier
                .padding(top = SPACING_SMALL)
                .clickable { uriHandler.openUri(infoUrl.value) },
        )
    }
}
