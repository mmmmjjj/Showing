import csv
from numpy import NaN
import selenium
import pandas as pd
from selenium.common.exceptions import NoSuchElementException
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from konlpy.tag import Okt
import time

### review txt 파일 생성
def makeReview(playdb_id):
    file = open(f"./data/review/{playdb_id}_review.txt", "w", encoding="utf-8")
    review_lump = " ".join(review_list)
    file.write(" ".join(okt.nouns(review_lump)))
    file.close()
    review_list.clear()


#### 공연 리뷰 정보 추출 ####
def showReview(interpark_id):
    driver.get(show_url.format(interpark_id))
    driver.implicitly_wait(5)

    try:
        driver.find_element_by_css_selector(
            "#root > div.productsLayer.productsLayerMinorGuide.active > div > div.buttonLayerCloseWrap > button.buttonLayerClose"
        ).click()
    except NoSuchElementException as e:
        print(e)

    # 탭 이동
    driver.find_element_by_class_name("productsTabPosts").click()

    # 더 보기 누르기
    num = 0
    while num <= 100:
        try:
            driver.find_element_by_css_selector(
                "#epilogueTabContent > button"
            ).send_keys(Keys.ENTER)
            time.sleep(0.4)
            num += 1
        except Exception as e:
            print(e)
            break

    reviews = driver.find_element_by_css_selector(
        "#writerInfo"
    ).find_elements_by_tag_name("li")

    for el in reviews:
        title = (
            el.find_element_by_class_name("userBoardTitle")
            .find_element_by_tag_name("b")
            .text
        )
        content = el.find_element_by_class_name("boardContentTxt").get_attribute(
            "innerHTML"
        )
        review_list.append(title)
        review_list.append(content)


# -------------------- main --------------------
options = webdriver.ChromeOptions()
options.add_experimental_option("excludeSwitches", ["enable-logging"])

# 크롬 드라이버 (에러 나면 절대 경로로 바꾸기)
driver = webdriver.Chrome(executable_path="./chromedriver", options=options)
# url
show_url = "https://mobileticket.interpark.com/goods/{}"
# KoNLPY
okt = Okt()
# 리뷰 목록 list
review_list = []
# 인터파크, 플레이db csv
data = pd.read_csv("./data/season_db.csv")
for i in data.index:
    if data["interpark_id"][i] == data["interpark_id"][i]:
        start_time = time.time()
        print(data["interpark_id"][i], data["playdb_id"][i])
        try:
            showReview(data["interpark_id"][i])
        except Exception:
            continue
        print("리뷰 크롤링 완료")
        makeReview(data["playdb_id"][i])
        print("분석 완료, 수행시간 : ", time.time() - start_time)

print("전체 완료")
