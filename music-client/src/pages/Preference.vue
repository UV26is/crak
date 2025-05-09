<template>
  <div class="preference">
    <div class="preference-container">
      <h2>偏好设置</h2>
      
      <!-- 音乐偏好设置 -->
      <el-card class="preference-card">
        <div slot="header">
          <span>音乐偏好</span>
        </div>
        <el-form :model="preferenceForm" ref="preferenceForm" label-width="100px">
          <el-form-item label="音乐风格">
            <el-select v-model="preferenceForm.genres" multiple placeholder="请选择您喜欢的音乐风格">
              <el-option label="流行" value="pop"></el-option>
              <el-option label="摇滚" value="rock"></el-option>
              <el-option label="民谣" value="folk"></el-option>
              <el-option label="电子" value="electronic"></el-option>
              <el-option label="古典" value="classical"></el-option>
              <el-option label="爵士" value="jazz"></el-option>
            </el-select>
          </el-form-item>
          
          <el-form-item label="语言偏好">
            <el-select v-model="preferenceForm.languages" multiple placeholder="请选择您喜欢的语言">
              <el-option label="中文" value="zh"></el-option>
              <el-option label="英语" value="en"></el-option>
              <el-option label="日语" value="ja"></el-option>
              <el-option label="韩语" value="ko"></el-option>
            </el-select>
          </el-form-item>
          
          <el-form-item label="音质偏好">
            <el-radio-group v-model="preferenceForm.quality">
              <el-radio label="standard">标准音质</el-radio>
              <el-radio label="high">高品质</el-radio>
              <el-radio label="lossless">无损音质</el-radio>
            </el-radio-group>
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="savePreference">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <!-- 推荐歌曲 -->
      <el-card class="recommend-card">
        <div slot="header">
          <span>为您推荐</span>
        </div>
        <el-table :data="recommendedSongs" style="width: 100%">
          <el-table-column prop="name" label="歌曲名称"></el-table-column>
          <el-table-column prop="singer" label="歌手"></el-table-column>
          <el-table-column prop="genre" label="风格"></el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button size="mini" @click="playSong(scope.row)">播放</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <!-- 历史记录 -->
      <el-card class="history-card">
        <div slot="header">
          <span>历史记录</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="clearHistory">清空历史</el-button>
        </div>
        <el-table :data="historyList" style="width: 100%">
          <el-table-column prop="name" label="歌曲名称"></el-table-column>
          <el-table-column prop="singer" label="歌手"></el-table-column>
          <el-table-column prop="playTime" label="播放时间"></el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button size="mini" @click="playSong(scope.row)">播放</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { getUserPreference, updateUserPreference, getRecommendedSongs, getUserHistory, clearUserHistory } from '../api/preference'

export default {
  name: 'preference',
  mixins: [mixin],
  data() {
    return {
      preferenceForm: {
        genres: [],
        languages: [],
        quality: 'standard'
      },
      recommendedSongs: [],
      historyList: []
    }
  },
  created() {
    this.getPreference()
    this.getRecommendations()
    this.getHistory()
  },
  methods: {
    getPreference() {
      getUserPreference().then(res => {
        if (res.code === 1) {
          this.preferenceForm = res.data
        }
      })
    },
    savePreference() {
      updateUserPreference(this.preferenceForm).then(res => {
        if (res.code === 1) {
          this.notify('设置保存成功', 'success')
          this.getRecommendations()
        }
      })
    },
    getRecommendations() {
      getRecommendedSongs().then(res => {
        if (res.code === 1) {
          this.recommendedSongs = res.data
        }
      })
    },
    getHistory() {
      getUserHistory().then(res => {
        if (res.code === 1) {
          this.historyList = res.data
        }
      })
    },
    clearHistory() {
      this.$confirm('确认清空历史记录？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        clearUserHistory().then(res => {
          if (res.code === 1) {
            this.notify('历史记录已清空', 'success')
            this.historyList = []
          }
        })
      })
    },
    playSong(song) {
      // 实现播放功能
      this.$store.commit('setIsActive', true)
      this.$store.commit('setSongId', song.id)
    }
  }
}
</script>

<style lang="scss" scoped>
.preference {
  padding: 20px;
  .preference-container {
    max-width: 1200px;
    margin: 0 auto;
    
    h2 {
      text-align: center;
      margin-bottom: 30px;
      color: #409EFF;
    }
    
    .preference-card,
    .recommend-card,
    .history-card {
      margin-bottom: 20px;
    }
  }
}
</style> 