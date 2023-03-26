import io
import sys

import requests
import os
from lxml import etree
import re
import json
import eyed3

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf8')
furl = 'https://www.bilibili.com/video/'
requests.packages.urllib3.disable_warnings()
header = {
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 '
                  'Safari/537.36 Edg/108.0.1462.54',
    'Referer': 'https://www.bilibili.com/'}


def get_url_bilibili(url, savedir, fname, aut):
    session = requests.session()
    res = session.get(url=url, headers=header, verify=False)
    _elem = etree.HTML(res.content)
    videoPlayInfo = str(_elem.xpath('//head/script[3]/text()')[0].encode('utf-8').decode('utf-8'))[20:]
    videoJson = json.loads(videoPlayInfo)
    try:
        voiURL = videoJson['data']['dash']['video'][0]['baseUrl']
        audURL = videoJson['data']['dash']['audio'][0]['baseUrl']
        form = 0
    except Exception:
        voiURL = videoJson['data']['durl'][0]['url']
        form = 1
    '''
    print('视频url:', voiURL)
    print('音频url:', audURL)
    print('flag:', form)
    '''
    savedir = savedir.encode('utf-8').decode('utf-8')
    if not os.path.exists(savedir):
        os.makedirs(savedir)
        # print('下载目录创建成功！')
    if form == 1:
        # print('正在下载视频中。。。')
        downFile(homeurl=url, url=voiURL, name=savedir + fname + '_Video.mp4', session=session)
        # print('视频下载成功，保存在', savedir)
        # print('提取音频中。。。')
        ffcmd = r'D:/ffmpeg-master-latest-win64-gpl/ffmpeg-master-latest-win64-gpl/bin/ffmpeg -i ' + savedir + fname + \
                '_Video.mp4' + ' -vn ' + savedir + fname + '.mp3'
        try:
            os.system(ffcmd)
            # print('分离成功')
        except Exception as e:
            print('Split audio error')
    if form == 0:
        # print('下载音频中。。。')
        downFile(homeurl=url, url=audURL, name=savedir + fname + '.m4s', session=session)
        ffcmd = r'D:/ffmpeg-master-latest-win64-gpl/ffmpeg-master-latest-win64-gpl/bin/ffmpeg -i ' + savedir + fname + \
                '.m4s ' + savedir + fname + '.mp3 -loglevel quiet'
        try:
            os.system(ffcmd)
            # print('转mp3成功')
            os.remove(savedir + fname + '.m4s')
            setatsfile = eyed3.load(savedir + fname + '.mp3')
            setatsfile.tag.artist = aut
            setatsfile.tag.save()
        except Exception as e:
            print('Audio transfer to mp3 error')
        print('#DownOver')


def downFile(homeurl, url, name, session=requests.session()):
    header.update({'Refer': homeurl})
    session.options(url=url, headers=header, verify=False)
    begin = 0
    end = 1024 * 512 - 1
    flag = 0
    while True:
        header.update({'Range': 'bytes=' + str(begin) + '-' + str(end)})
        res = session.get(url=url, headers=header, verify=False)
        if res.status_code != 416:
            begin = end + 1
            end += 1024 * 512
        else:
            header.update({'Range': str(end + 1) + '-'})
            res = session.get(url=url, headers=header, verify=False)
            flag = 1
        with open(name.encode('utf-8').decode('utf-8'), 'ab') as fp:
            fp.write(res.content)
        if flag == 1:
            fp.close()
            break


def cmddown():
    order = sys.argv[1]
    if order == "down":
        url = sys.argv[2]
        savedir = sys.argv[3]
        fname = sys.argv[4]
        aut = sys.argv[5]
        get_url_bilibili(url, savedir, fname, aut)
    elif order == "ser":
        out = sser(sys.argv[2])
        jsonpath = sys.argv[3]
        with open(jsonpath, 'w') as jf:
            json.dump(out, jf, indent=4)
            jf.close()
            print("#SearchOver")

# get_url_bilibili("https://www.bilibili.com/video/BV1sx411A778/", 'D:/BilibiliDown/', "music", "佚名")


def sser(order):
    surl = "https://search.bilibili.com/all?keyword=" + order
    session = requests.session()
    i = 1
    temporder = order.lower()
    res = session.get(url=surl, headers=header, verify=False)
    _elemser = etree.HTML(res.content)
    divinfo = []
    ruler = re.compile(temporder)
    if str(_elemser.xpath('//body/div[3]/div/div[2]/div[2]/div/div/div/div[1]/@class')) == "['bangumi-pgc-list i_wrapper search-all-list']":
        hasbangumi = 2
    elif str(_elemser.xpath('//body/div[3]/div/div[2]/div[2]/div/div/div/div[1]/@class')) == "['activity-game-list i_wrapper search-all-list']":
        hasbangumi = 3
    else:
        hasbangumi = 1
    while True:
        urlinfo = str(_elemser.xpath('//body/div[3]/div/div[2]/div[2]/div/div/div/div[' + str(hasbangumi) + ']/div/div[' + str(i) + ']/div/div[2]/a/@href')).encode('utf-8').decode('utf-8')
        titleinfo = str(_elemser.xpath('//body/div[3]/div/div[2]/div[2]/div/div/div/div[' + str(hasbangumi) + ']/div/div[' + str(i) + ']/div/div[2]/div/div/a/h3/@title')).encode('utf-8').decode('utf-8')
        autinfo = str(_elemser.xpath('//body/div[3]/div/div[2]/div[2]/div/div/div/div[' + str(hasbangumi) + ']/div/div[' + str(i) + ']/div/div[2]/div/div/p/a/span[1]/text()')).encode('utf-8').decode('utf-8')
        titleinfo = titleinfo.replace(' ', '')
        titleinfo = titleinfo.replace('\\u3000', '')
        titleinfo = titleinfo.replace('[\'', '')
        titleinfo = titleinfo.replace('\']', '')
        urlinfo = urlinfo.replace('[\'//', '')
        urlinfo = urlinfo.replace('\']', '')
        autinfo = autinfo.replace('[\'', '')
        autinfo = autinfo.replace('\']', '')
        print(titleinfo)
        print(autinfo)
        print(urlinfo)
        temprus = titleinfo.lower()
        if ruler.search(temprus) and i < 20:
            print(titleinfo)
            divinfo.append({'name': titleinfo,
                            'url': urlinfo,
                            'aut': autinfo})
        elif i >= 20:
            break
        i += 1
    return divinfo


cmddown()
# ser a D:\BilibiliDown\bili.json
# Shots(BroilerRemix)油管原版 D:\project\java\MusicGo\src\main\resources\music\ https://www.bilibili.com/video/BV1yD4y1m7y5/ -暮秋廿五-
# get_url_bilibili("https://www.bilibili.com/video/BV1yD4y1m7y5/", r"D:\project\java\MusicGo\src\main\resources\music\\", "Shots(BroilerRemix)油管原版", "-暮秋廿五-")
