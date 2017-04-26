package com.minivac.mini.log

import android.app.Application
import com.minivac.mini.dagger.AppScope
import com.minivac.mini.flux.LazyStoreMap
import com.minivac.mini.flux.OnActivityLifeCycleAction
import com.minivac.mini.flux.OnActivityLifeCycleAction.ActivityStage.*
import com.minivac.mini.flux.Store
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Inject


@AppScope
class LoggerStore @Inject constructor(context: Application, val lazyStoreMap: LazyStoreMap)
    : Store<LoggerState>() {

    private val fileLogController = FileLogController(context)

    override fun initialState() = LoggerState()
    override fun init() {
        val fileTree = fileLogController.newFileTree()
        if (fileTree != null) {
            Grove.plant(fileTree)
        }

        dispatcher.subscribe(OnActivityLifeCycleAction::class) {
            when (it.stage) {
                PAUSED, STOPPED, DESTROYED -> fileTree?.flush()
                else -> { //No-op
                }
            }
        }
        dispatcher.addInterceptor(LoggerInterceptor(lazyStoreMap.get().values))
    }
}

class LoggerState

@Module
abstract class LoggerModule {
    @Binds @IntoMap @ClassKey(LoggerStore::class)
    abstract fun provideLoggerStoreToMap(store: LoggerStore): Store<*>
}