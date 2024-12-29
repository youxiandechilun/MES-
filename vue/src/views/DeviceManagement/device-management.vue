<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="data.deviceId" style="width: 260px;margin-right: 10px" placeholder="请输入设备编号查询"
                :prefix-icon="Search"/>
      <el-input v-model="data.worker" style="width: 260px;margin-right: 10px" placeholder="请输入操作工查询"
                :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:10px" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table :data="data.tableData" style="width: 100%">
        <el-table-column prop="deviceId" label="设备编号"></el-table-column>
        <el-table-column prop="deviceName" label="设备名称"></el-table-column>
        <el-table-column prop="worker" label="员工姓名"></el-table-column>
        <el-table-column prop="processName" label="工序名称"></el-table-column>
        <el-table-column prop="useDate" label="使用日期"></el-table-column>
        <el-table-column prop="startTime" label="开始时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column prop="runtime" label="运行时间 (小时)" width="150"></el-table-column>
        <el-table-column prop="depreciation" label="折旧费用"></el-table-column>
        <el-table-column prop="electricityCost" label="电费"></el-table-column>
        <el-table-column prop="totalCost" label="总成本"></el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:pageSize="data.pageSize"
          v-model:currentPage="data.pageNum"
          @current-change="handelCurrentChange"
          @size-change="handelSizeChange"
          :page-sizes="[5,10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="data.total">
      </el-pagination>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from "vue";
import { Search } from "@element-plus/icons-vue";
import request from "@/utils/request";

const data = reactive({
  deviceId: '',
  worker: '',
  tableData: [],
  total: 0,
  pageSize: 5,
  pageNum: 1,
});

// 初始化页面加载
const load = () => {
  request.get("/deviceRecord/selectPage", {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      deviceId: data.deviceId,
      worker: data.worker,
    }
  }).then(res => {
    data.tableData = res.data?.list || [];
    data.total = res.data?.total || 0;
  });
};
load();

// 翻页事件
const handelCurrentChange = () => {
  load();
};
const handelSizeChange = () => {
  load();
};

// 重置事件
const reset = () => {
  data.deviceId = '';
  data.worker = '';
  load();
};
</script>

<style scoped>
.card {
  padding: 20px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>