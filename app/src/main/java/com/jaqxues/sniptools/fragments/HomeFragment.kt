package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.gravity
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.ui.AppScreen
import com.jaqxues.sniptools.utils.installedScVersion

class HomeFragment : BaseFragment() {
    override val menuId get() = R.id.nav_home
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreen()
            }
        }
    }
}

@Composable
@Preview
fun HomeScreen() {
    AppScreen {
        HomeContent()
    }
}

@Composable
fun HomeDivider() {
    Divider(Modifier.padding(horizontal = 8.dp), color = MaterialTheme.colors.primary)
}

@Composable
fun HomeContent() {
    Column(Modifier.fillMaxHeight()) {
        Column(
            Modifier.padding(16.dp).weight(1f),
            horizontalGravity = CenterHorizontally
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

        HomeDivider()
        Spacer(Modifier.height(16.dp))
        FooterText(
            annotatedString {
                append(ContextAmbient.current.getString(R.string.footer_app_version))
                append(": ")

                withStyle(SpanStyle(colorResource(R.color.colorPrimaryLight))) {
                    append(BuildConfig.VERSION_NAME)
                }
            }
        )
        FooterText(
            annotatedString {
                append(ContextAmbient.current.getString(R.string.footer_snapchat_version))
                append(": ")
                withStyle(SpanStyle(colorResource(R.color.colorPrimaryLight))) {
                    append(ContextAmbient.current.installedScVersion ?: "Unknown")
                }
            }
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun FooterText(string: AnnotatedString) {
    Text(
        text = string,
        modifier = Modifier.gravity(CenterHorizontally),
        color = Color.LightGray,
        fontSize = 12.sp
    )
}
