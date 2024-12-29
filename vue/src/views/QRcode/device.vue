<template>
  <div class="container">
    <h1 class="title">设备信息</h1>
    <el-form :model="deviceInfo" ref="formRef" label-width="120px">
      <el-form-item label="设备编号">
        <el-input v-model="deviceInfo.deviceId" disabled></el-input>
      </el-form-item>
      <el-form-item label="设备名称">
        <el-input v-model="deviceInfo.deviceName" disabled></el-input>
      </el-form-item>
      <el-form-item label="折旧率">
        <el-input v-model="deviceInfo.depreciationRate" suffix="%" disabled></el-input>
      </el-form-item>
      <el-form-item label="电费率">
        <el-input v-model="deviceInfo.electricityRate" suffix="kWh" disabled></el-input>
      </el-form-item>
      <el-form-item label="员工姓名">
        <el-input v-model="worker" placeholder="请输入员工姓名"></el-input>
      </el-form-item>
      <el-form-item label="工序名称">
        <el-input v-model="processName" placeholder="请输入工序名称"></el-input>
      </el-form-item>
      <el-form-item label="当前时间">
        <el-input v-model="currentTime" disabled></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRoute } from "vue-router";
import request from "@/utils/request"; // 确保这个请求函数已经定义
import { ElMessage } from "element-plus";

const route = useRoute();
const deviceInfo = ref({});
const worker = ref('');
const processName = ref('');
const currentTime = ref('');
const formRef = ref(null);
let timer = null;

// 格式化日期
const formatDate = (date) => {
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
};

// 格式化日期时间
const formatDateTime = (date) => {
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const seconds = date.getSeconds().toString().padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

const submit = () => {
  request.get('/deviceRecord/select1', { params: { deviceId: deviceInfo.value.deviceId } })
      .then(res => {
        if (res.data === 0) {
          request.post('/deviceRecord/add1', {
            deviceId: deviceInfo.value.deviceId,
            deviceName: deviceInfo.value.deviceName,
            worker: worker.value,
            processName: processName.value,
            depreciationRate: deviceInfo.value.depreciationRate,
            electricityRate: deviceInfo.value.electricityRate,
            useDate: formatDate(new Date()), // 获取当前系统日期
            startTime: currentTime.value
          }).then(res => {
            ElMessage.success('设备开启成功');
          }).catch(error => {
            console.error(error);
            ElMessage.error('提交失败');
          });
        } else if (res.data > 0) {
          request.post('/deviceRecord/add2', {
            deviceId: deviceInfo.value.deviceId,
            endTime: formatDateTime(new Date()) // 获取当前系统时间
          }).then(res => {
            ElMessage.success('设备关闭成功');
          }).catch(error => {
            console.error(error);
            ElMessage.error('提交失败');
          });
        }
      })
      .catch(error => {
        console.error(error);
        ElMessage.error('获取设备记录失败');
      });
};

onMounted(() => {
  const query = route.query;
  if (query.data) {
    try {
      const data = JSON.parse(decodeURIComponent(query.data));
      deviceInfo.value = {
        deviceId: data.deviceId,
        deviceName: data.deviceName,
        depreciationRate: data.depreciationRate,
        electricityRate: data.electricityRate
      };
    } catch (error) {
      console.error("解析查询参数失败:", error);
    }
  }
  timer = setInterval(() => {
    currentTime.value = formatDateTime(new Date()); // 每秒更新当前时间
  }, 1000);
});

onBeforeUnmount(() => {
  clearInterval(timer);
});
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 20px;
}
.title {
  font-size: 24px;
  margin-bottom: 20px;
}
</style>