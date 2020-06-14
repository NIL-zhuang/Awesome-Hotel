import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login.vue'

Vue.use(VueRouter)
const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    redirect: '/NJUSE'
  },
  {
    path: '/NJUSE',
    name: 'layout',
    redirect: '/homePage',
    component: ()=> import('@/views/layout'),
    children: [
      {
        path: '/homePage',
        name: 'home',
        component: () => import('@/views/main/homePage')
      },
      {
        path: '/hotel/searchHotel',
        name: 'searchHotel',
        component: () => import('@/views/hotel/searchHotel')
      },
      {
        path: '/hotel/hotelDetail/:hotelId',
        name: 'hotelDetail',
        component: () => import('@/views/hotel/hotelDetail')
      },
      {
        path: '/successOrder',
        name: 'successOrder',
        component: () => import('@/views/order/successOrder')
      },
      {
        path: '/user/info/:userId',
        name: 'userInfo',
        component: () => import('@/views/user/info')
      },
      {
        path: '/manager/info/:userId',
        name: 'managerInfo',
        component: () => import('@/views/hotelManager/hotelManagerInfo')
      },
      {
        path: '/user/membership',
        name: 'userMembership',
        component: () => import('@/views/user/userMembership')
      },
      // {
      //   path: '/hotel/membership',
      //   name: 'hotelMembership',
      //   component: () => import('@/views/hotelManager/hotelMembership')
      // },
      {
        path: '/hotelManager/manageHotel',
        name: 'manageHotel',
        component: () => import('@/views/hotelManager/manageHotel')
      },
      {
        path: '/admin/manageUser',
        name: 'manageUser',
        component: () => import('@/views/admin/manageUser')
      },
      {
        path: '/admin/adminInfo',
        name: 'adminInfo',
        component: () => import('@/views/admin/adminInfo')
      },
      {
        path: '/salesPerson/manageSiteCoupon',
        name: 'manageSiteCoupon',
        component: () => import('@/views/salesPerson/siteCoupon')
      },
      {
        path: '/salesPerson/manageOrders',
        name: 'manageOrders',
        component: () => import('@/views/salesPerson/manageOrders')
      },
      {
        path: '/salesPerson/manageUserCredit',
        name: 'manageUserCredit',
        component: () => import('@/views/salesPerson/manageUserCredit')
      },
      {
        path: '/salesPerson/manageMembership',
        name: 'manageMembership',
        component: () => import('@/views/salesPerson/manageMembership')
      }

    ]
  },
]
const createRouter = () => new VueRouter({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes
})
const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
