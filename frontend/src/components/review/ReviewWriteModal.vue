<template>
  <div>
    <modal :show.sync="modals.writeReview">
      <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="setWriteModalStates(false)">
        &times;
      </button>
      <br />
      <br />
      <div v-if="seasonShow.length !== null">
        <div style="width: 100%; position: relative">
          <div class="showInfo left mb-3">
            <div>
              <h3>{{ seasonShowName }}</h3>
              <p style="font-size: 13px">{{ seasonShow.startDate }}&nbsp;~&nbsp;{{ seasonShow.endDate }}</p>
            </div>
            <div class="form-group picker">
              <div class="input-group">
                <label for="date" class="bold pickerlabel">관람일정</label>
                <div class="input-group-prepend">
                  <span class="input-group-text"><i class="ni ni-calendar-grid-58"></i></span>
                </div>
                <datepicker
                  v-if="seasonShow.startDate"
                  class="form-control"
                  input-class="smaller"
                  v-model="review.showDate"
                  :disabledDates="{
                    to: new Date(seasonShow.startDate),
                    from: new Date(seasonShow.endDate) > new Date() ? new Date() : new Date(seasonShow.endDate),
                  }"
                  :openDate="new Date(year, month, day)"
                >
                </datepicker>
              </div>
            </div>
            <div class="form-group picker">
              <div class="input-group">
                <label for="time" class="bold pickerlabel">관람시간</label>
                <div class="input-group-prepend">
                  <span class="input-group-text"><i class="fa fa-clock-o"></i></span>
                </div>
                <input type="time" class="form-control" v-model="review.showTime" />
              </div>
            </div>
            <label for="location" class="bold">관람장소</label>
            <p class="inline" type="input">{{ seasonShow.location }}</p>
            <br />
          </div>

          <div class="right mb-3">
            <img class="showimage" :src="seasonShow.seasonImage" alt="show image" />
          </div>
        </div>
        <div class="cast">
          <label class="bold left">캐스팅</label>
          <label style="font-size: 6px; margin-left: 10px">관람한 공연의 캐스트를 선택해주세요</label><br />
          <span
            class="badge badge-pill casting badge-primary"
            v-for="(index, key) in seasonShow.actorList"
            :key="key"
            :id="`write` + index.castingId"
            @click="selectactors(index.castingId)"
          >
            {{ index.actorName }}
          </span>
        </div>
        <div class="content">
          <p style="margin: 8%">
            <b-textarea v-model="review.reviewContent" id="showcontent"></b-textarea>
          </p>
        </div>
      </div>
      <template slot="footer">
        <base-button type="white" @click="addReview">Save</base-button>
        <base-button type="link" class="ml-auto" @click="setWriteModalStates(false)">Cancle </base-button>
      </template>
    </modal>
  </div>
</template>

<script>
import Modal from "@/components/Modal.vue";
import Datepicker from "vuejs-datepicker";
import { addMyReview } from "@/api/review.js";
import { mapState, mapActions } from "vuex";

const reviewStore = "reviewStore";
const userStore = "userStore";
export default {
  name: "ReviewWriteModal",
  components: {
    Modal,
    Datepicker,
  },
  props: {
    performance: String,
    seasonShow: Object,
    seasonShowName: String,
    setwrite: Number,
  },

  data() {
    return {
      review: {
        seasonId: this.seasonShow.seasonId,
        showDate: "",
        showTime: "",
        castingIdList: [],
        reviewContent: "",
      },
    };
  },
  computed: {
    ...mapState(reviewStore, ["modals"]),
    ...mapState(userStore, ["userInfo"]),
    year: function () {
      return new Date(this.seasonShow.startDate).getFullYear();
    },
    month: function () {
      return new Date(this.seasonShow.startDate).getMonth();
    },
    day: function () {
      return new Date(this.seasonShow.startDate).getDate();
    },
  },
  methods: {
    ...mapActions(reviewStore, ["setWriteReviewModalState"]),
    setWriteModalStates(status) {
      this.setWriteReviewModalState(status);
    },
    // 배우 캐스팅 구하기
    selectactors(id) {
      // 클릭된 블록
      let cur = document.getElementById(`write` + id);
      // 해당 블록이 이미 선택되었으면(warning으로 변해 있으면)
      if (cur.className == "badge badge-pill casting badge-warning") {
        // 다시 primary로 변경하고 선택된 actors에서 뺌
        cur.className = "badge badge-pill casting badge-primary";
        for (var i = 0; i < this.review.castingIdList.length; i++) {
          if (this.review.castingIdList[i] === id) {
            this.review.castingIdList.splice(i, 1);
            i--;
          }
        }
      } else {
        // 선택 안된 블록이라면 warning으로 변경하고 목록에 저장
        cur.className = "badge badge-pill casting badge-warning";
        this.review.castingIdList.push(id);
      }
    },
    // Review 추가하기
    addMyReview,
    addReview() {
      if (!this.review.showDate) {
        this.showToast("error", "관람일정을 선택해주세요.");
        return;
      }
      if (!this.review.showTime) {
        this.showToast("error", "관람시간을 선택해주세요.");
        return;
      }
      if (!this.review.reviewContent) {
        this.showToast("error", "리뷰 내용을 입력해주세요.");
        return;
      }

      this.dateFommatter(this.review.showDate);
      this.timeFommatter(this.review.showTime);
      var reviewInfo = {
        seasonId: this.seasonShow.seasonId,
        showDate: this.review.showDate,
        showTime: this.review.showTime,
        castingIdList: this.review.castingIdList,
        reviewContent: this.review.reviewContent,
        userId: this.userInfo.userId,
      };
      this.addMyReview(
        reviewInfo,
        (response) => {
          this.setWriteReviewModalState(false);
          this.showToast("success", response.data.message);
          if (this.review.castingIdList.length !== 0) {
            this.review.castingIdList.forEach((el) => {
              let cur = document.getElementById(`write` + el);
              cur.className = "badge badge-pill casting badge-primary";
            });
          }
          this.review = {
            seasonId: this.seasonShow.seasonId,
            showDate: "",
            showTime: "",
            castingIdList: [],
            reviewContent: "",
          };
          this.$emit("setWrite", this.setwrite + 1);
          this.$emit("myReviewList");
          this.$emit("addMyReview");
        },
        (fail) => {
          console.log(fail);
          this.showToast("error", "리뷰 작성 실패");
        }
      );
    },
    // 날짜 포맷 정리
    dateFommatter(date) {
      var month = date.getMonth() + 1;
      if (month < 10) month = "0" + month;
      var day = date.getDate();
      if (day < 10) day = "0" + day;
      var tmp = date.getFullYear() + "-" + month + "-" + day;
      this.review.showDate = tmp;
    },
    // 시간 포맷 정리
    timeFommatter(time) {
      time = time + ":00";
      this.review.showTime = time;
    },
    // confirm 메시지 표시
    showToast(typeName, message) {
      this.$toast(message, {
        type: typeName,
      });
    },
  },
  updated() {},
};
</script>

<style scoped>
/* * {
  font-family: "Noto Sans" !important;
   font-size: 15px;
} */
.inline {
  display: inline;
  margin-left: 10px;
}
.profile {
  width: 5%;
  height: 5%;
  object-fit: cover;
  border-radius: 70%;
}
.title {
  margin-bottom: 4%;
}
.showimage {
  width: 95%;
  float: right;
  margin-right: 5%;
  border-radius: 5%;
}
div.left {
  width: 60%;
  float: left;
  box-sizing: border-box;
  /* margin-left: 5%; */
}
div.right {
  width: 40%;
  float: right;
  box-sizing: border-box;
}
.content {
  clear: both;
  height: 70%;
  background-color: #f8f8f8;
  border-radius: 5%;
}
.backArrow {
  clear: both;
  float: left;
  display: block;
  background-color: transparent;
  border: 0;
}
.backArrow:not(:disabled):not(.disabled) {
  cursor: pointer;
}
.bold,
h3 {
  font-weight: bold;
}
#showcontent {
  border: 0px;
  width: 100%;
  height: 200px;
  resize: none;
  background-color: transparent;
  outline-color: transparent;
}
.picker {
  width: 90%;
}
.pickerlabel {
  margin-top: 5%;
  margin-right: 10px;
}
.cast {
  clear: both;
}
input[type="time"]::-webkit-calendar-picker-indicator {
  background-image: url("./assets/pngwing.png");
  background-size: 12px 12px;
  border-color: transparent;
}
.modalbutton {
  width: 20%;
}
.badge {
  cursor: pointer;
}
</style>

<style>
.smaller {
  height: 50% !important;
  width: 100% !important;
  border: 0px !important;
  color: darkgrey;
}
</style>
