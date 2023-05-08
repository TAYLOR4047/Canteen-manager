import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import './assets/gloable.css'


import request from "@/utils/request";
import Axios from "axios";
import store from "@/store/store";

Vue.config.productionTip = false;
Vue.use(ElementUI, {size: "small"});

Vue.prototype.request = request
Vue.prototype.$axios=Axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
