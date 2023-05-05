<template>
  <!--     页面主体       -->
    <div>
        <!--        搜索部分        -->
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"
                      v-model="name"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <!--        表格内部操作部分        -->
        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="cid" label="ID" width="80"></el-table-column>
            <el-table-column  label="图片预览" width="80">
                <template slot-scope="scope">
                    <el-popover placement="top-start" title="" trigger="hover">
                        <img :src="scope.row.image" alt="" style="width: 150px;height: 150px">
                        <img slot="reference" :src="scope.row.image" style="width: 30px;height: 30px">
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="餐品名称"></el-table-column>
            <el-table-column prop="num" label="点餐数量"></el-table-column>
            <el-table-column prop="price" label="价格"></el-table-column>
            <el-table-column label="操作" width="350" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" @click="Drop(scope.row)">减少<i class="el-icon-menu"></i>
                    </el-button>
                    <el-button type="primary" @click="Add(scope.row)">新增<i class="el-icon-menu"></i>
                    </el-button>
                    <el-button type="danger" @click="DeleteFromCart(scope.row)">取消此菜品<i class="el-icon-menu"></i>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--       翻页与页码部分         -->
        <div style="padding: 10px 0">

            <div>
                <div>
                    购物车总价为 :
                </div>

            <el-button style="float: right" type="danger" @click="DeleteFromCart(scope.row)">结算<i class="el-icon-menu"></i>
            </el-button>
            </div>
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<!--页面数据与动作Js代码-->
<script>
export default {
    name: "Menu",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            name: "",
            form: {},
            multipleSelection: [],
            menuData: [],
            props: {
                label: 'name',
            },
            expends: [],
            checks: [],
            dishId: 0,
        }
    },
    // 请求分页查询数据
    created() {
        this.load()
    },
    methods: {
        // 将数据库查询操作封装
        load() {
            this.request.get("/cart/pageUser", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.records
                this.total = res.total

            });
        },
        updateDish() {
            this.request.post("/cart", this.form).then(res => {
                if (res) {
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.dialogFormVisible = false
                    this.load()
                    this.$message.error("保存失败")
                }
            })
        },
        insertDish() {
            this.request.post("/cart/insert", this.form).then(res => {
                if (res) {
                    this.$message.success("保存成功")
                    this.dialogInsertFormVisible = false
                    this.load()
                } else {
                    this.dialogInsertFormVisible = false
                    this.$message.error("保存失败")
                    this.load()
                }
            })
        },
        handleAdd() {
            this.dialogInsertFormVisible = true
            this.form = {}
        },
        handleEdit(row) {
            this.form = row
            this.dialogFormVisible = true
        },
        del(id) {
            this.request.delete("/cart/" + id).then(res => {
                if (res) {
                    this.$message.success("删除成功")
                    this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
        },
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delBatch() {
            let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
            this.request.post("/cart/del/batch", ids).then(res => {
                if (res) {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.name = ""
            this.load()
        },
        // 动态分页请求
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        changeEnable(row) {
            this.request.post("/cart/update", row).then(res => {
                if (res.code === '200') {
                    this.$message.success("操作成功")
                }
            })
        },
        filterStatus(value, row) {
            return row.status === value;
        },
    }
}
</script>

<!--表格头部样式-->
<style>
.headerBg {
    background: #eee !important;
}
</style>