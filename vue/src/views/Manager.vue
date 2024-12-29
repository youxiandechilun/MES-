<template>
  <div>
    <div
        style="height: 60px; background-color: #fff; display: flex; align-items: center; border-bottom: 1px solid #ddd">
      <div style="flex: 1">
        <div style="padding-left: 20px; display: flex; align-items: center">
          <img src="@/assets/imgs/logo.png" alt="" style="width: 40px">
          <div style="font-weight: bold; font-size: 24px; margin-left: 5px">MES管理系统</div>
        </div>
      </div>
      <div style="width: fit-content; padding-right: 10px; display: flex; align-items: center;">
        <img :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png' " alt=""
             style="width: 40px;
              border-radius: 50%;
              height: 40px">
        <span style="margin-left: 5px">{{ user.name }}</span>
      </div>
    </div>

    <div style="display: flex">
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu
            router
            style="border: none"
            :default-active="$route.path"
            :default-openeds="['/home','1']"
        >

          <el-menu-item index="/home">
            <el-icon>
              <HomeFilled/>
            </el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <Share/>
              </el-icon>
              <span>组织架构</span>
            </template>
            <el-menu-item index="/userOrganization" v-if="user.role !==4 ">
              <el-icon>
                <Avatar/>
              </el-icon>
              <span>用户管理</span>
            </el-menu-item>
            <el-menu-item index="/workshopOrganization">
              <el-icon>
                <Platform/>
              </el-icon>
              <span>车间管理</span>
            </el-menu-item>
            <el-menu-item index="/teamGroupsOrganization">
              <el-icon>
                <School/>
              </el-icon>
              <span>班组管理</span>
            </el-menu-item>
            <el-menu-item index="/customer">
              <el-icon>
                <User/>
              </el-icon>
              <span>客户管理</span>
            </el-menu-item>
            <el-menu-item index="/positionOrganization">
              <el-icon>
                <Position/>
              </el-icon>
              <span>岗位管理</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <Checked/>
              </el-icon>
              <span>考勤管理</span>
            </template>
            <el-menu-item index="/workerCheck">
              <el-icon>
                <List/>
              </el-icon>
              <span>员工考勤</span>
            </el-menu-item>
            <el-menu-item index="/moneyCheck">
              <el-icon>
                <Money/>
              </el-icon>
              <span>薪资管理</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              <el-icon>
                <Memo/>
              </el-icon>
              <span>设备管理</span>
            </template>

            <el-menu-item index="/listDevice">
              <el-icon>
                <Document/>
              </el-icon>
              <span>设备列表</span>
            </el-menu-item>

            <el-menu-item index="/deviceDevice">
              <el-icon>
                <Document/>
              </el-icon>
              <span>设备管理</span>
            </el-menu-item>

            <el-menu-item index="/runDevice">
              <el-icon>
                <Compass/>
              </el-icon>
              <span>设备运行</span>
            </el-menu-item>
            <el-menu-item index="/maintenanceDevice">
              <el-icon>
                <Tools/>
              </el-icon>
              <span>设备维修</span>
            </el-menu-item>
            <el-menu-item index="/UpkeepDevice">
              <el-icon>
                <RefreshRight/>
              </el-icon>
              <span>设备保养</span>
            </el-menu-item>
          </el-sub-menu>


          <el-sub-menu index="4">
            <template #title>
              <el-icon>
                <ScaleToOriginal/>
              </el-icon>
              <span>材料管理</span>
            </template>
            <el-menu-item index="/matterMaterial">
              <el-icon>
                <Box/>
              </el-icon>
              <span>物料管理</span>
            </el-menu-item>
            <el-menu-item index="/toolMaterial">
              <el-icon>
                <DocumentAdd/>
              </el-icon>
              <span>工装器具管理</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="5">

            <template #title>
              <el-icon>
                <Edit/>
              </el-icon>
              <span>订单管理</span>
            </template>


            <el-menu-item index="/contract">
              <el-icon>
                <Notebook/>
              </el-icon>
              <span>合同管理</span>
            </el-menu-item>

            <el-menu-item index="/sales-order">
              <el-icon>
                <edit/>
              </el-icon>
              <span>销售订单管理</span>
            </el-menu-item>

            <el-menu-item index="/purchase-order">
              <el-icon>
                <ShoppingCart/>
              </el-icon>
              <span>采购订单管理</span>
            </el-menu-item>


          </el-sub-menu>

          <el-sub-menu index="6">

            <template #title>
              <el-icon>
                <Open/>
              </el-icon>
              <span>生产管理</span>
            </template>

            <el-menu-item index="/productionPlan">
              <el-icon>
                <Film/>
              </el-icon>
              <span>生产计划</span>
            </el-menu-item>

            <el-menu-item index="/tickets">
              <el-icon>
                <Grid/>
              </el-icon>
              <span>工单管理</span>
            </el-menu-item>

            <el-menu-item index="/dailyPlan">
              <el-icon>
                <Menu/>
              </el-icon>
              <span>员工每日生产计划</span>
            </el-menu-item>

            <el-menu-item index="/process-handover">
              <el-icon>
                <Menu/>
              </el-icon>
              <span>工序交接</span>
            </el-menu-item>


            <el-menu-item index="/waxPatternProduce">
              <el-icon>
                <ChromeFilled/>
              </el-icon>
              <span>蜡模车间</span>
            </el-menu-item>

            <el-menu-item index="/shellPatternProduce">
              <el-icon>
                <Eleme/>
              </el-icon>
              <span>制壳车间</span>
            </el-menu-item>
            <el-menu-item index="/meltProduce">
              <el-icon>
                <ElemeFilled/>
              </el-icon>
              <span>融化车间</span>
            </el-menu-item>
            <el-menu-item index="/postProcessingProduce">
              <el-icon>
                <ElementPlus/>
              </el-icon>
              <span>后处理车间</span>
            </el-menu-item>
            <el-menu-item index="/machiningProduce">
              <el-icon>
                <Shop/>
              </el-icon>
              <span>机加工车间</span>
            </el-menu-item>
          </el-sub-menu>


          <el-sub-menu index="7">

            <template #title>
              <el-icon>
                <House/>
              </el-icon>
              <span>仓库管理</span>
            </template>

            <el-menu-item index="/material">
              <el-icon>
                <Notification/>
              </el-icon>
              <span>仓库管理</span>
            </el-menu-item>

            <el-menu-item index="/materialRecords">
              <el-icon>
                <Right/>
              </el-icon>
              <span>出库管理</span>
            </el-menu-item>

            <el-menu-item index="/inWarehouse">
              <el-icon>
                <Back/>
              </el-icon>
              <span>入库管理</span>
            </el-menu-item>

          </el-sub-menu>

          <el-menu-item index="/person">
            <el-icon>
              <User/>
            </el-icon>
            <span>个人资料</span>
          </el-menu-item>
          <el-menu-item index="login" @click="logout">
            <el-icon>
              <SwitchButton/>
            </el-icon>
            <span>退出系统</span>
          </el-menu-item>
        </el-menu>
      </div>

      <div style="flex: 1; width: 0; background-color: #f8f8ff; padding: 10px">
        <router-view/>
      </div>
    </div>

  </div>
</template>

<script setup>
import {useRoute} from 'vue-router'

const $route = useRoute()
const user = JSON.parse(localStorage.getItem('users') || '{}')

const logout = () => {
  localStorage.removeItem('users')
}
</script>

<style scoped>
.el-menu-item.is-active {
  background-color: #dcede9 !important;
}

.el-menu-item:hover {
  color: #11A983;
}

:deep(th) {
  color: #333;
}
</style>
