<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="searchParams.ticketID" style="width: 260px;margin-right: 10px" placeholder="工单编号"
                :prefix-icon="Search"/>
      <el-input v-model="searchParams.worker" style="width: 260px;margin-right: 10px" placeholder="工人名称"
                :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:10px" @click="loadTickets">查询</el-button>
      <el-button type="info" @click="resetSearch">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 10px">
      <el-table :data="ticketsData" style="width: 100%">
        <el-table-column prop="ticketID" label="工单编号"></el-table-column>
        <el-table-column prop="planID" label="计划编号"></el-table-column>
        <el-table-column prop="productName" label="产品名称"></el-table-column>
        <el-table-column prop="workshop" label="车间"></el-table-column>
        <el-table-column prop="team" label="班组"></el-table-column>
        <el-table-column prop="worker" label="工人名字"></el-table-column>
        <el-table-column prop="grade" label="工人工级"></el-table-column>
        <el-table-column prop="process" label="工序"></el-table-column>
        <el-table-column prop="number" label="所需完成的数量" width="150"></el-table-column>
        <el-table-column prop="startDate" label="开始日期"></el-table-column>
        <el-table-column prop="endDate" label="结束日期"></el-table-column>
      </el-table>
    </div>
    <div class="card">
      <el-pagination
          v-model:currentPage="searchParams.pageNum"
          v-model:pageSize="searchParams.pageSize"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :page-sizes="[5, 10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script setup>
import {ref, reactive} from "vue";
import {Search} from "@element-plus/icons-vue";
import request from "@/utils/request";

const searchParams = reactive({
  ticketID: '',
  worker: '', // 注意这里，我们使用worker作为搜索参数
  pageNum: 1,
  pageSize: 5,
});

const ticketsData = ref([]);
const total = ref(0);

// 初始化页面加载
const loadTickets = () => {
  request.get("/ticket/selectPage", {
    params: {
      pageNum: searchParams.pageNum,
      pageSize: searchParams.pageSize,
      ticketID: searchParams.ticketID,
      worker: searchParams.worker, // 将worker作为搜索参数发送
    }
  }).then(res => {
    ticketsData.value = res.data?.list || [];
    total.value = res.data?.total || 0;
  });
};
loadTickets();

// 翻页事件
const handleCurrentChange = (newPage) => {
  searchParams.pageNum = newPage;
  loadTickets();
};
const handleSizeChange = (newSize) => {
  searchParams.pageSize = newSize;
  loadTickets();
};

// 重置搜索参数
const resetSearch = () => {
  searchParams.ticketID = '';
  searchParams.worker = ''; // 重置worker搜索参数
  searchParams.pageNum = 1;
  searchParams.pageSize = 5;
  loadTickets();
};
</script>

<style scoped>
.card {
  margin-bottom: 10px;
}
</style>