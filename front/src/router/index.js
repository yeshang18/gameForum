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
      },
      {
        path:"/article/:articleId",
        component:()=>import('../views/ArticleDetail.vue')
      },
      {
        path:"/newArticle",
        component:()=>import('../views/ArticlePost.vue')
      },
      {
        path:"/user/:userId",
        component:()=>import('../views/Ucenter.vue')
      },
      {
        path:"/feedback",
        component:()=>import('../views/Feedback.vue')
      },
    ]
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
