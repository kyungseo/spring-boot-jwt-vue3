import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import VueGoodTablePlugin from 'vue-good-table-next';
import 'vue-good-table-next/dist/vue-good-table-next.css'

import { FontAwesomeIcon } from './plugins/font-awesome'

import apiInterceptors from './services/apiInterceptors';

apiInterceptors(store);

const offset = (new Date().getTimezoneOffset());
const today = new Date(((new Date()).getTime() - (offset * 60 * 1000)))
  .toISOString()
  .split('T')[0];

createApp(App)
  .use(router)
  .use(store)
  .use(VueGoodTablePlugin)
  .component("font-awesome-icon", FontAwesomeIcon)
  .provide('today', today)
  .mount("#app");
