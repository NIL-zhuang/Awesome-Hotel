const getters = {
    //user
    token: state => state.user.token,
    userId: state => state.user.userId,
    userInfo: state => state.user.userInfo,
    dateRange: state => state.user.dateRange,
    userOrderList: state => state.user.userOrderList,
    onceOrderedList: state => state.user.onceOrderedList,
    hotelListLoading: state => state.hotel.hotelListLoading,
    hotelList: state => state.hotel.hotelList,
    currentHotelInfo: state => state.hotel.currentHotelInfo,
    currentHotelId: state => state.hotel.currentHotelId,
    confirmModalVisible: state => state.hotel.confirmModalVisible,
    orderModalVisible: state => state.hotel.orderModalVisible,
    currentOrderRoom: state => state.hotel.currentOrderRoom,
    orderMatchCouponList: state => state.hotel.orderMatchCouponList,
    registerMembershipModalVisible: state => state.user.registerMembershipModalVisible,
    //admin
    managerList: state => state.admin.managerList,
    addManagerModalVisible: state => state.admin.addManagerModalVisible,
    addManagerParams: state => state.admin.addManagerParams,
    //hotelManager
    manageHotelId: state => state.hotelManager.manageHotelId,
    orderList: state => state.hotelManager.orderList,
    addHotelModalVisible: state => state.hotelManager.addHotelModalVisible,
    addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,
    couponVisible: state => state.hotelManager.couponVisible,
    addCouponVisible: state => state.hotelManager.addCouponVisible,
    activeHotelId: state => state.hotelManager.activeHotelId,
    couponList: state => state.hotelManager.couponList,
    //salesPerson
    allOrderList: state => state.salesPerson.allOrderList,
    handleAbnormalOrderVisible: state => state.salesPerson.handleAbnormalOrderVisible,
    addSiteCouponVisible: state => state.salesPerson.addSiteCouponVisible,
    currentUserId: state => state.salesPerson.currentUserId,
    currentUserInfo: state => state.salesPerson.currentUserInfo,
    //order
    orderDetailVisible: state => state.order.orderDetailVisible,
    orderInfo: state => state.order.orderInfo,
}

export default getters
