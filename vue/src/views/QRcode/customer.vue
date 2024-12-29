<template>
  <div class="container">
    <h1 class="title">客户信息</h1>
    <div v-if="formData" class="form-container">
      <el-form :model="formData" label-width="120px">

        <el-form-item label="合同编号">
          <el-input v-model="formData.contractNumber"></el-input>
        </el-form-item>

        <el-form-item label="客户名称">
          <el-input v-model="formData.customerName"></el-input>
        </el-form-item>
        <el-form-item label="客户编号">
          <el-input v-model="formData.customerCode"></el-input>
        </el-form-item>
        <el-form-item label="客户电话">
          <el-input v-model="formData.customerPhone"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div v-else class="form-container">
      <p>请扫描二维码以查看表单。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const formData = ref(null);

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