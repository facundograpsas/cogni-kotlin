package com.example.kotlin.state.fallaciesState

class FallaciesGameStateManager {
    fun getNextState(currentState: FallaciesGameStates, action: FallaciesGameAction): FallaciesGameStates {
        return when (action) {
            is FallaciesGameAction.Start -> FallaciesGameStates.Loading
            is FallaciesGameAction.QuestionsFetched -> FallaciesGameStates.Loaded(
                action.question,
                action.index,
                action.score,
                action.questions
            )
            is FallaciesGameAction.FetchError -> FallaciesGameStates.Failure(action.message)
            is FallaciesGameAction.CompleteFallaciesGame -> FallaciesGameStates.Complete
            is FallaciesGameAction.UpdateFallaciesGameData -> FallaciesGameStates.Loaded(
                action.newQuestion!!,
                action.newIndex,
                action.newScore,
                isUpdated = true,
                questions = action.questions
            )
        }
    }
}