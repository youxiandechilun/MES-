<template>
  <div class="container">
    <h1 class="title">今日生产计划</h1>
    <div v-if="formData" class="form-container">
      <el-form :model="formData" label-width="120px">
        <el-form-item label="产品名称">
          <el-input v-model="formData.productName"></el-input>
        </el-form-item>
        <el-form-item label="工人名字">
          <el-input v-model="formData.worker"></el-input>
        </el-form-item>
        <el-form-item label="工序">
          <el-input v-model="formData.process"></el-input>
        </el-form-item>
        <el-form-item label="所需完成的数量">
          <el-input v-model="formData.number"></el-input>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-input v-model="formData.startDate"></el-input>
        </el-form-item>
        <el-form-item label="结束日期">
          <el-input v-model="formData.endDate"></el-input>
        </el-form-item>
        <el-form-item>
          <div>每日平均完成数量: {{ averagePerDay }}</div>
          <div>当前日期: {{ currentDate }}</div>
        </el-form-item>
      </el-form>
    </div>
    <div v-else class="form-container">
      <p>请扫描二维码以查看工单信息。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import dayjs from "dayjs";

const route = useRoute();
const formData = ref(null);

// 计算每日平均完成数量
const averagePerDay = computed(() => {
  if (!formData.value || !formData.value.startDate || !formData.value.endDate || !formData.value.number) {
    return 0;
  }
  const start = dayjs(formData.value.startDate);
  const end = dayjs(formData.value.endDate);
  const diff = end.diff(start, 'days');
  return Math.ceil(formData.value.number / diff);
});

// 计算当前日期
const currentDate = computed(() => dayjs().format('YYYY-MM-DD'));

onMounted(() => {
  const query = route.query;
  if (query.data) {
    try {
      formData.value = JSON.parse(decodeURIComponent(query.data));
    } catch (error) {
      console.error("解析二维码数据失败:", error);
    }
  }
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

.form-container {
  max-width: 600px;
  width: 100%;
  padding: 20px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>