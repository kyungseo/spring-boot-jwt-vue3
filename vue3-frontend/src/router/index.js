import { createWebHistory, createRouter } from "vue-router";
import { defineComponent } from 'vue'
//import store from '../store'

import Home from "../components/Home.vue";

import AuthLogin from "../components/auth/Login.vue";
import AuthRegister from "../components/auth/Register.vue";

// lazy-loaded
const AdminHome = () => import("../components/admin/AdminHome.vue")
const UserList = () => import("../components/admin/securedSample/UserList.vue")
const StaffHome = () => import("../components/staff/StaffHome.vue")
const UserHome = () => import("../components/user/UserHome.vue")
const UserProfile = () => import("../components/user/UserProfile.vue")

const NotFound = defineComponent({
  template: '<div>Not Found</div>',
})

const routes = [
  {
    path: "/",
    redirect: "/home"
  },
  {
    path: "/home",
    component: Home,
  },
  {
    path: "/auth/login",
    component: AuthLogin,
  },
  {
    path: "/auth/register",
    component: AuthRegister,
  },
  {
    path: "/admin",
    name: "adminHome",
    component: AdminHome,
  },
  {
    path: "/secured/sample",
    name: "securedSampleUserList",
    component: UserList,
  },
  {
    path: "/staff",
    name: "staffHome",
    component: StaffHome,
  },
  {
    path: "/user",
    name: "userHome",
    component: UserHome,
  },
  {
    path: "/user/profile",
    name: "userProfile",
    component: UserProfile,
  },
  {
    path: '/:catchAll(.*)+',
    component: NotFound
  }
];

const router = createRouter({
  history: createWebHistory(),
  linkActiveClass: 'active',
  routes,
});

/*
router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register', '/home'];
  const authRequired = !publicPages.includes(to.path);
  //const loggedIn = localStorage.getItem('user');

  if (authRequired && !store.getters.isLoggedIn) {
    next('/login');
  } else {
    next();
  }
});
*/

export default router;