import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        // 登录和注册页面
        {
            path: '/login',
            name: 'Login',
            component: () => import('@/views/Login.vue')
        },
        {
            path: '/register',
            name: 'Register',
            component: () => import('@/views/Register.vue')
        },

        // 根路径重定向到登录页面
        {
            path: '/',
            redirect: '/login'
        },

        // QRcode 相关页面
        {
            path: '/qrcustomer',
            name: 'Rrcustomer',
            component: () => import('@/views/QRcode/customer.vue')
        },
        {
            path: '/qrcustomer1',
            name: 'Rrcustomer1',
            component: () => import('@/views/QRcode/dailyPlan.vue')
        },
        {
            path: '/device',
            name: 'Device',
            component: () => import('@/views/QRcode/device.vue')
        },

        // 管理界面，包含多个子模块
        {
            path: '/manager',
            name: 'Manager',
            component: () => import('@/views/Manager.vue'),
            children: [
                // 组织结构
                {
                    path: '/customer',
                    name: 'Customer',
                    component: () => import('@/views/OrganizationalStructure/customer.vue')
                },

                // 订单管理
                {
                    path: '/contract',
                    name: 'Contract',
                    component: () => import('@/views/OrderManagement/contract.vue')
                },
                {
                    path: '/sales-order',
                    name: 'Sales-order',
                    component: () => import('@/views/OrderManagement/sales-order.vue')
                },
                {
                    path: '/purchase-order',
                    name: 'Purchase-order',
                    component: () => import('@/views/OrderManagement/purchase-order.vue')
                },
                {
                    path: '/dailyPlan',
                    name: 'DailyPlan',
                    component: () => import('@/views/OrderManagement/dailyPlan.vue')
                },
                {
                    path: '/process-handover',
                    name: 'Process-handover',
                    component: () => import('@/views/OrderManagement/process-handover.vue')
                },

                // 生产管理
                {
                    path: '/productionPlan',
                    name: 'ProductionPlan',
                    component: () => import('@/views/ProductionManagement/productionPlan.vue')
                },
                {
                    path: '/tickets',
                    name: 'Tickets',
                    component: () => import('@/views/ProductionManagement/tickets.vue')
                },

                // 物料管理
                {
                    path: '/material',
                    name: 'Material',
                    component: () => import('@/views/MaterialManagement/Material.vue')
                },
                {
                    path: '/materialRecords',
                    name: 'MaterialRecords',
                    component: () => import('@/views/MaterialManagement/MaterialRecords.vue')
                },

                // 设备管理
                {
                    path: '/deviceDevice',
                    name: 'DeviceDevice',
                    component: () => import('@/views/DeviceManagement/device-management.vue')
                },
                {
                    path: '/listDevice',
                    name: 'ListDevice',
                    component: () => import('@/views/DeviceManagement/device-list.vue')
                },

                // 主页
                {
                    path: '/home',
                    name: 'Home',
                    component: () => import('@/views/Manager/Home.vue')
                }
            ]
        },

        // 404错误页面
        {
            path: '/:pathMatch(.*)*',
            name: 'NotFound',
            component: () => import('@/views/errorPage/404.vue')
        }
    ]
});

export default router;