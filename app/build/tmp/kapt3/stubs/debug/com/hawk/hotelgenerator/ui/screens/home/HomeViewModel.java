package com.hawk.hotelgenerator.ui.screens.home;

/**
 * 首页 ViewModel
 *
 * 负责管理酒店点评生成的核心流程，包括：
 * 1. 酒店模糊搜索建议的实时拉取（带防抖）。
 * 2. 设备地理位置的权限校验与获取。
 * 3. 驱动大模型生成评价并持久化存储至历史记录。
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0010J\u0006\u0010\"\u001a\u00020\u001fJ\u0006\u0010#\u001a\u00020\u001fJ\u0006\u0010$\u001a\u00020\u001fJ\u000e\u0010%\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\'J\u0010\u0010(\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\u000eH\u0002J\u000e\u0010*\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u0011J\u000e\u0010,\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020.J\u000e\u0010/\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020.J\u000e\u00100\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u000eJ\u000e\u00102\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020.J\u000e\u00103\u001a\u00020\u001f2\u0006\u00104\u001a\u00020\u000eJ\u000e\u00105\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020.J\u000e\u00106\u001a\u00020\u001f2\u0006\u00107\u001a\u00020\u000eJ\u000e\u00108\u001a\u00020\u001f2\u0006\u00109\u001a\u00020:J\u0006\u0010;\u001a\u00020\u001fR\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2 = {"Lcom/hawk/hotelgenerator/ui/screens/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "llmRepository", "Lcom/hawk/hotelgenerator/data/repository/LlmRepository;", "mapRepository", "Lcom/hawk/hotelgenerator/data/repository/MapRepository;", "historyRepository", "Lcom/hawk/hotelgenerator/data/repository/HistoryRepository;", "userPrefsRepository", "Lcom/hawk/hotelgenerator/data/repository/UserPreferencesRepository;", "(Lcom/hawk/hotelgenerator/data/repository/LlmRepository;Lcom/hawk/hotelgenerator/data/repository/MapRepository;Lcom/hawk/hotelgenerator/data/repository/HistoryRepository;Lcom/hawk/hotelgenerator/data/repository/UserPreferencesRepository;)V", "_customRoomTypes", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "_searchResults", "", "Lcom/hawk/hotelgenerator/data/model/HotelPoi;", "_uiState", "Lcom/hawk/hotelgenerator/ui/screens/home/HomeUiState;", "customRoomTypes", "Lkotlinx/coroutines/flow/StateFlow;", "getCustomRoomTypes", "()Lkotlinx/coroutines/flow/StateFlow;", "searchJob", "Lkotlinx/coroutines/Job;", "searchResults", "getSearchResults", "uiState", "getUiState", "addImages", "", "uris", "Landroid/net/Uri;", "clearError", "clearSearchResults", "generateReview", "removeImage", "index", "", "searchHotels", "keyword", "selectHotel", "poi", "updateEnvironment", "value", "", "updateFacilities", "updateHotelName", "name", "updateHygiene", "updateRoomType", "type", "updateService", "updateStayDate", "date", "updateStyle", "style", "Lcom/hawk/hotelgenerator/data/model/ReviewStyle;", "useCurrentLocation", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hawk.hotelgenerator.data.repository.LlmRepository llmRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hawk.hotelgenerator.data.repository.MapRepository mapRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hawk.hotelgenerator.data.repository.HistoryRepository historyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hawk.hotelgenerator.data.repository.UserPreferencesRepository userPrefsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hawk.hotelgenerator.ui.screens.home.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hawk.hotelgenerator.ui.screens.home.HomeUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _customRoomTypes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> customRoomTypes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.hawk.hotelgenerator.data.model.HotelPoi>> _searchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hawk.hotelgenerator.data.model.HotelPoi>> searchResults = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job searchJob;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.repository.LlmRepository llmRepository, @org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.repository.MapRepository mapRepository, @org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.repository.HistoryRepository historyRepository, @org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.repository.UserPreferencesRepository userPrefsRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hawk.hotelgenerator.ui.screens.home.HomeUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getCustomRoomTypes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hawk.hotelgenerator.data.model.HotelPoi>> getSearchResults() {
        return null;
    }
    
    public final void updateHotelName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    private final void searchHotels(java.lang.String keyword) {
    }
    
    public final void selectHotel(@org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.model.HotelPoi poi) {
    }
    
    public final void clearSearchResults() {
    }
    
    public final void useCurrentLocation() {
    }
    
    public final void updateRoomType(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
    }
    
    public final void updateStayDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date) {
    }
    
    public final void updateHygiene(float value) {
    }
    
    public final void updateEnvironment(float value) {
    }
    
    public final void updateFacilities(float value) {
    }
    
    public final void updateService(float value) {
    }
    
    public final void updateStyle(@org.jetbrains.annotations.NotNull()
    com.hawk.hotelgenerator.data.model.ReviewStyle style) {
    }
    
    public final void addImages(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> uris) {
    }
    
    public final void removeImage(int index) {
    }
    
    /**
     * 调用 AI 生成点评
     */
    public final void generateReview() {
    }
    
    public final void clearError() {
    }
}