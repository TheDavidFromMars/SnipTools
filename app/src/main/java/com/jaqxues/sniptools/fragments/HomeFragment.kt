package com.jaqxues.sniptools.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.utils.installedScVersion

@Composable
fun HomeScreen() {
    Column(
        Modifier.fillMaxHeight(),
        horizontalAlignment = CenterHorizontally
    ) {
        HomeContent(Modifier.weight(1f))


        HomeDivider()
        Spacer(Modifier.height(16.dp))
        HomeFooter()
    }
}

@Composable
fun HomeDivider() {
    Divider(Modifier.padding(horizontal = 8.dp), color = MaterialTheme.colors.primary)
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier.padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Image(
            imageResource(R.drawable.sniptools_logo),
            Modifier.padding(32.dp).preferredHeight(72.dp)
        )
        HomeDivider()
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.home_description),
            textAlign = TextAlign.Center
        )
        HomeDivider()
    }
}

@Composable
fun AnnotatedString.Builder.Highlight(action: @Composable AnnotatedString.Builder.() -> Unit) {
    withStyle(SpanStyle(colorResource(R.color.colorPrimaryLight))) { action() }
}

@Composable
fun HomeFooter() {
    Providers(AmbientContentAlpha provides ContentAlpha.medium) {
        Text(buildAnnotatedString {
            append("Author: ")
            Highlight { append("jaqxues") }
        }, fontSize = 12.sp)

        Text(buildAnnotatedString {
            append(AmbientContext.current.getString(R.string.footer_app_version))
            append(": ")
            Highlight { append(BuildConfig.VERSION_NAME) }
        }, fontSize = 12.sp)

        Text(buildAnnotatedString {
            append(AmbientContext.current.getString(R.string.footer_snapchat_version))
            append(": ")
            Highlight { append(AmbientContext.current.installedScVersion ?: "Unknown") }
        }, fontSize = 12.sp)
    }
    Spacer(Modifier.height(16.dp))
}