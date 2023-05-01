<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a href="/" class="navbar-brand">KYUNGSEO.PoC</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/home" class="nav-link">
            <font-awesome-icon icon="home" /> Home
          </router-link>
        </li>
        <li v-if="showAdminHome" class="nav-item">
          <router-link to="/admin" class="nav-link">Admin</router-link>
        </li>
        <li v-if="showStaffHome" class="nav-item">
          <router-link to="/staff" class="nav-link">Staff</router-link>
        </li>
        <li class="nav-item">
          <router-link v-if="currentUser" to="/user" class="nav-link">User</router-link>
        </li>
        <li v-if="showAdminHome" class="nav-item">
          <router-link to="/secured/sample" class="nav-link">Sample</router-link>
        </li>
      </div>

      <div v-if="!currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/auth/register" class="nav-link">
            <font-awesome-icon icon="user-plus" /> Sign Up
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/auth/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" /> Login
          </router-link>
        </li>
      </div>

      <div v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/user/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt" /> LogOut
          </a>
        </li>
      </div>
    </nav>

    <div class="container">
      <toast />
      <div id="notification" />
      ※ 오늘 일자: <em>{{ today }}</em>
      <div><br /></div>
      <router-view />
    </div>
  </div>
</template>

<script>
import { ref, provide, inject } from 'vue'
import EventBus from "./common/EventBus";
import Toast from './modules/Toast.vue'

export default {
  name: 'App',
  setup() {
    const toast = ref('')
    provide('toast', toast)
    const today = inject('today')
    return { today }
  },
  components: {
    Toast
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminHome() {
      if (this.currentUser && this.currentUser['roles']) {
        return this.currentUser['roles'].includes('ROLE_ADMIN');
      }

      return false;
    },
    showStaffHome() {
      if (this.currentUser && this.currentUser['roles']) {
        return this.currentUser['roles'].includes('ROLE_STAFF');
      }

      return false;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout").then(
        () => {
          this.$router.push('/auth/login');
        }
      );
    }
  },
  mounted() {
    EventBus.on("logout", () => {
      this.logOut();
    });
  },
  beforeUnmount() {
    EventBus.remove("logout");
  }
};
</script>
