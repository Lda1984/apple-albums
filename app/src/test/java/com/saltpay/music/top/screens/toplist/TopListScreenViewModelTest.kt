package com.saltpay.music.top.screens.toplist

import com.saltpay.music.top.MockModel
import com.saltpay.music.top.TestDispatcherProviderImpl
import com.saltpay.music.top.domian.usecase.album.FetchAlbumUseCase
import com.saltpay.music.top.domian.usecase.album.GetAlbumsByNameUseCase
import com.saltpay.music.top.domian.usecase.album.HandleFavoriteUseCase
import com.saltpay.music.top.ui.screens.common.model.NetworkIssueState
import com.saltpay.music.top.ui.screens.toplist.TopListEvent.*
import com.saltpay.music.top.ui.screens.toplist.TopListScreenViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class TopListScreenViewModelTest {

    private val dispatcherProvider: TestDispatcherProviderImpl = TestDispatcherProviderImpl()

    private val fetchAlbumUseCase: FetchAlbumUseCase = mockk {
        coEvery<Unit> { this@mockk.invoke() } answers { nothing }
    }

    private val getAlbumsByNameUseCase: GetAlbumsByNameUseCase = mockk {
        coEvery { this@mockk.invoke(any()) } returns flowOf(emptyList())
    }

    private val handleFavoriteUseCase: HandleFavoriteUseCase = mockk {
        coEvery { this@mockk.invoke(any()) } answers { nothing }
    }


    private lateinit var subject: TopListScreenViewModel

    @Before
    fun setup() {
        subject = TopListScreenViewModel(
            dispatcherProvider = dispatcherProvider,
            fetchAlbumUseCase = fetchAlbumUseCase,
            getAlbumsByNameUseCase = getAlbumsByNameUseCase,
            handleFavoriteUseCase = handleFavoriteUseCase

        )
    }

    @Test
    fun `Init viewModel`() = runTest {
        assertEquals("", subject.searchFlow.value)
        assertEquals(false, subject.isAutoUpdate.value)
        assertEquals(NetworkIssueState.NoIssue, subject.networkIssue.value)
        assertEquals(true, subject.isUpdateProcess.value)

        dispatcherProvider.getTestScheduler().advanceTimeBy(3000)

        coVerify(exactly = 1) { fetchAlbumUseCase() }
        coVerify(exactly = 1) { getAlbumsByNameUseCase("") }
        assertEquals(false, subject.isUpdateProcess.value)
    }

    @Test
    fun `User search album`() = runTest {
        dispatcherProvider.getTestScheduler().advanceTimeBy(3000)

        assertEquals("", subject.searchFlow.value)
        coVerify(exactly = 1) { getAlbumsByNameUseCase("") }

        subject.handleEvent(ChangeFilter("input"))

        assertEquals("input", subject.searchFlow.value)
        coVerify(exactly = 1) { getAlbumsByNameUseCase("input") }
    }

    @Test
    fun `User uses autoUpdate`() = runTest {
        subject.handleEvent(ChangeAutoUpdate(true))
        dispatcherProvider.getTestScheduler().advanceTimeBy(20000)

        coVerify(atLeast = 4) { fetchAlbumUseCase() }
    }

    @Test
    fun `User marks favorite`() = runTest {
        subject.handleEvent(HandleFavorite(MockModel.simpleEntity))

        coVerify(exactly = 1) { handleFavoriteUseCase(MockModel.simpleEntity) }
    }

    @Test
    fun `Fetch issue`() = runTest {
        val exception = IOException()
        coEvery { fetchAlbumUseCase() } throws exception
        assertEquals(true, subject.isUpdateProcess.value)

        dispatcherProvider.getTestScheduler().advanceTimeBy(3000)

        assertEquals(NetworkIssueState.Issue(exception), subject.networkIssue.value)
        assertEquals(false, subject.isUpdateProcess.value)
    }
}

