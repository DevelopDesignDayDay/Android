import com.ddd.attendance.check.DDDApplication
import com.ddd.attendance.check.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        ActivityBindingModule::class,
        AppModule::class,
        DataSource::class,
        RepositoryModule::class]
)
interface DDDComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: DDDApplication): Builder

        fun build(): DDDComponent
    }

    fun inject(application: DDDApplication)

}