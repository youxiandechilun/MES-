<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="data.worker" style="width: 260px;margin-right: 10px" placeholder="请输入工人名称查询"
                :prefix-icon="Search"/>
      <el-input v-model="data.materialName" style="width: 260px;margin-right: 10px" placeholder="请输入物料名称查询"
                :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:10px" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table :data="data.tableData" style="width: 100%">

        <el-table-column prop="materialId" label="物料编码"></el-table-column>
        <el-table-column prop="materialName" label="物料名称"></el-table-column>
        <el-table-column prop="quantityIssued" label="出库数量"></el-table-column>
        <el-table-column prop="worker" label="领取工人"></el-table-column>
        <el-table-column prop="issuanceDate" label="出库日期"></el-table-column>
        <el-table-column prop="purpose" label="出库目的"></el-table-column>
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
  worker: '',
  materialName: '',
  tableData: [],
  total: 0,
  pageSize: 5,
  pageNum: 1,
});

// 初始化页面加载
const load = () => {
  request.get("/materialrecords/selectPage", {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      worker: data.worker,
      materialName: data.materialName,
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
  data.materialName = '';
  data.materialCode = '';
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