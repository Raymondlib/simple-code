'''
对9种分类器进行比较，看看分类器种类对于bagging 的影响
对于30个不同的数据文件，对比两个指标s1，s2
在同一张图上，画8个线，对应一种分类器的效果,横坐标是dataset，纵坐标是两种指标,分析分类器种类的影响
'''
import matplotlib.pyplot as plt
import os
import linecache
filename_list =["NaiveBayes","J48","zeroR","RandomForest","DecisionStump","RandomTree","REPTree","DecisionTable","multiple"]
s1="rootMSE"
s2="precision"
#s is a choose
s=s1
x=[]
# fig = plt.figure(facecolor='white')

# ay = fig.add_subplot(111)
# ax.plot(x2, label='9-randomforest')
# plt.plot(x3, label='9-j48')
y=[]
for k in range(3,50,3):
    y.append(k)


data_name=[]
file_dir = 'C:/Users/25755/Desktop/1级队列/数据集'
    #file_dir = 'C:/Users/25755/Desktop/已训练过的big_data'
path = os.listdir(file_dir)
for i in range(0, 30):
    data_name.append(path[i])

#图的数量对应 k的取值数量,k is num of classifer
k_list =[3,6,9,21]
y=[]
# for k in range(0, 2, 1):
#     y.append(k)
#y 代表data set
# y=[0,1,2,3]
for k in range(0, 27):
    y.append(k)
#j 是 k_list 的索引
for j in range(len(k_list)):
    fig = plt.figure(facecolor='white')
    #m 是分类器种类
    for m in range(8):
        x = []
        for i in range(len(y)):
            filename='D:/project/java/leetcode/out/' + filename_list[m] + s + '_' + data_name[i] + '_.txt'
            f = open(filename, 'r')
            #读取分类器数量为k时的数据，也就是行数为j

            # x.append(float(f.readline()))
            x.append(float(linecache.getline(filename, j+1)))
        ax = fig.add_subplot(111)
        ax.plot(y, x, label=filename_list[m])

        # f = open('D:/project/java/leetcode/out/' + filename_list[j] + s + '_' + data_name[i] + '_.txt', 'r')
    plt.plot()
    plt.xlabel("dataset id")
    plt.ylabel(s)
    plt.title("num of classifiers = "+str(k_list[j]))
    plt.legend()
    plt.savefig('C:/Users/25755/Desktop/机器学习作业/数据图/分类器种类不同/'+"分类器数量="+str(k_list[j])+"_"+s+'_.png')
    plt.show()

quit()
# data_name=['anneal','anneal.ORIG']
#len(data_name)
for j in range(8):
    fig = plt.figure(facecolor='white')
    for i in range (2):
        f = open('D:/project/java/leetcode/out/' + filename_list[j] + s + '_' + data_name[i] + '_.txt', 'r')
        x = []
        for line in f:
            x.append(float(line[:-1]))
        ax = fig.add_subplot(111)

        ax.plot(y, x, label="dataset"+str(i+1))
    plt.plot()
    plt.xlabel("num of classfier")
    plt.ylabel(s)
    plt.title(filename_list[j])
    plt.legend()
    # plt.savefig('C:/Users/25755/Desktop/机器学习作业/数据图/分类器数量不同/' + data_name + '_' + s + '_.png')
    plt.savefig('C:/Users/25755/Desktop/机器学习作业/数据图/分类器数量不同/'+filename_list[j]+s+'_.png')
    plt.show()
quit()
for i in range(8):
    f=open('D:/project/java/leetcode/'+filename_list[i]+s+'_'+data_name+'_.txt','r')
    x=[]
    for line in f:
        x.append(float(line[:-1]))
    ax = fig.add_subplot(111)

    ax.plot(y,x, label=filename_list[i])

plt.plot()
plt.xlabel("num of classfier")
plt.ylabel(s)
plt.legend()
plt.savefig('C:/Users/25755/Desktop/机器学习作业/数据图/分类器数量不同/'+data_name+'_'+s+'_.png')
plt.show()



quit()
f1 =open('C:/Users/25755/Desktop/'+'9个贝叶斯.txt','r')
f2 = open('C:/Users/25755/Desktop/'+'9随机森林.txt','r')
f3 =open('C:/Users/25755/Desktop/'+'9个j48.txt','r')
# f.readline()
x1 =[]
x2 =[]
x3 =[]
for line in f1:
    x1.append(float(line[:-1]))
for line in f2:
    x2.append(float(line[:-1]))
for line in f3:
    x3.append(float(line[:-1]))
# print(x1)
count=0
count1=0
count2=0
count3=0
print(type(x1[1]))

for i in range(30):
    if max(x1[i],x2[i],x3[i])==x1[i] :
        count1+=1
    if max(x1[i],x2[i],x3[i])==x2[i]:
        count2+=1
    if max(x1[i], x2[i], x3[i]) == x3[i]:
        count3+=1
print(count1)
print(count2)
print(count3)
fig = plt.figure(facecolor='white')
ax = fig.add_subplot(111)
ax.plot(x1, label='9-NaiveBayes')
ay = fig.add_subplot(111)
ax.plot(x2, label='9-randomforest')
plt.plot(x3, label='9-j48')
# 图例
#randomforest
#NaiveBayes
plt.legend()
plt.show()

