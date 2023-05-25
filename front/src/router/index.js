import { createRouter, createWebHashHistory } from 'vue-router'
import Home from "../views/Home.vue"

const routes = [
  {
    path:'/',
    component:Home,
    children:[{
      path:"/end",
      component:()=>import('../views/End.vue')
    },
    {
      path:"/",
      component:()=>import('../views/ArticleList.vue')
    }]
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
