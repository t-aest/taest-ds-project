import {createRouter, createWebHashHistory} from "vue-router"

const routes = [
    {path: "/", redirect: "/home/wel"},
    {
        path: "/home", component: () => import("../pages/home.vue"),
        children: [
            {
                path: "wel", // 默认子路由
                component: () => import("../pages/welcome.vue")
            },
            {
                path: "option", // 默认子路由
                component: () => import("../pages/buildOptions.vue")
            },{
                path: "chat", // 默认子路由
                component: () => import("../pages/aIChat.vue")
            },{
                path: "chatp", // 默认子路由
                component: () => import("../pages/ChatPlus.vue")
            }]
    },
    {path: "/wel", component: () => import("../components/TheWelcome.vue")},
];
const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router