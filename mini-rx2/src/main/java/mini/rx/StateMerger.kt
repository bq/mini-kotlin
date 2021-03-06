/*
 * Copyright 2021 HyperDevs
 *
 * Copyright 2020 BQ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mini.rx

import io.reactivex.Flowable
import mini.Store

/**
 * Combine multiple store state flowables into a list of type R. If multiple stores
 * change at the same time this will emit multiple times, one for each store.
 *
 * ```
 * mergeStates<Task> {
 *  merge(linesStore) { loadProductTask[productId] }
 *  merge(upsellStore) { upsellsTasks[productId] }
 * }
 * ```
 */
class StateMerger<R> {
    val storeAndMappers = ArrayList<Pair<Store<*>, () -> R>>()

    /** Add a new store + mapper to the flowable. */
    inline fun <S : Store<U>, U : Any> merge(store: S, crossinline mapper: (U.() -> R)) {
        storeAndMappers.add(store to { store.state.mapper() })
    }
}

/**
 * Same as `StateMerger` but it merges states that holds lists.
 */
class StateListMerger<T> {
    val storeAndMappers = ArrayList<Pair<Store<*>, () -> List<T>>>()

    /** Add a new store + mapper to the flowable.
     *  Default param is returned when the list is empty.
     */
    inline fun <S : Store<U>, U : Any> mergeList(
        store: S,
        default: T,
        crossinline mapper: (U.() -> List<T>)
    ) {
        storeAndMappers.add(store to {
            val list = store.state.mapper()
            if (list.isEmpty()) {
                listOf(default)
            } else {
                list
            }
        })
    }
}

/**
 * Builder function for [StateMerger].
 */
inline fun <R> mergeStates(hotStart: Boolean = true, crossinline builder: StateMerger<R>.() -> Unit): Flowable<List<R>> {
    return StateMerger<R>().apply { builder() }.flowable(hotStart)
}

/**
 * Builder function for [StatListMerger].
 */
inline fun <T> mergeListStates(hotStart: Boolean = true, crossinline builder: StateListMerger<T>.() -> Unit): Flowable<List<T>> {
    return StateListMerger<T>().apply { builder() }.flowable(hotStart)
}

/** Build the StateMerger into the final flowable. */
fun <R> StateMerger<R>.flowable(hotStart: Boolean = true): Flowable<List<R>> {
    return storeAndMappers
        .map { (store, fn) -> store.flowable(hotStart).select { fn() } }
        .reduce { acc, storeFlowable ->
            acc.mergeWith(storeFlowable)
        }
        .map {
            storeAndMappers.map { (_, fn) -> fn() }.toList()
        }
        .onBackpressureLatest()
}

/** Build the StateMerger into the final flowable. */
fun <T> StateListMerger<T>.flowable(hotStart: Boolean = true): Flowable<List<T>> {
    return storeAndMappers
        .map { (store, fn) -> store.flowable(hotStart).select { fn() } }
        .reduce { acc, storeFlowable ->
            acc.mergeWith(storeFlowable)
        }
        .map {
            storeAndMappers.flatMap { (_, fn) -> fn() }.toList()
        }
        .onBackpressureLatest()
}