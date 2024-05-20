/**
 * 基础路由
 * @type { *[] }
 */

const constantRouterMap = [
  {
    path: '/',
    name: 'home',
    redirect: { name: 'Home' },
    children: [
      {
        path: '/home',
        name: 'Home',
        component: () => import('@/views/Index.vue')
      },
    ]
  },
]

export default constantRouterMap
