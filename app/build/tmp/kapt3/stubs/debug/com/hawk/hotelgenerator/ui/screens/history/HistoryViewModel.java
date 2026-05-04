package com.hawk.hotelgenerator.ui.screens.history;

/**
 * 历史记录 ViewModel
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/hawk/hotelgenerator/ui/screens/history/HistoryViewModel;", "Landroidx/lifecycle/ViewModel;", "historyRepository", "Lcom/hawk/hotelgenerator/data/repository/HistoryRepository;", "(Lcom/hawk/hotelgenerator/data/repository/HistoryRepository;)V", "historyItems", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/hawk/hotelgenerator/data/repository/ReviewHistory;", "getHistoryItems", "()Lkotlinx/coroutines/flow/StateFlow;", "clearAll", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HistoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hawk.hotelgenerator.data.repository.HistoryRepository historyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hawk.hotelgenerator.data.repository.ReviewHistory>> historyItems = null;
    
    @javax.inject.Inject()
    public HistoryViewModel(@org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.repository.HistoryRepository historyRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hawk.hotelgenerator.data.repository.ReviewHistory>> getHistoryItems() {
        return null;
    }
    
    public final void clearAll() {
    }
}