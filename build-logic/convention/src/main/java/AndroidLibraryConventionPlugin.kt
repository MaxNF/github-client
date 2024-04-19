import com.android.build.gradle.LibraryExtension
import com.maksimbagalei.githubclient.addLibraryPlugins
import com.maksimbagalei.githubclient.configureAndroidLibrary
import com.maksimbagalei.githubclient.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            addLibraryPlugins()
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureAndroidLibrary(this)
            }
        }
    }
}