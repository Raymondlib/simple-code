'''
对9种分类器进行比较，看看分类器种类对于bagging 的影响
对于30个不同的数据文件，对比两个指标s1，s2
一张图画一个数据集上，8种分类器，随着分类器数量变化的效果
批量画30张图
'''
import matplotlib.pyplot as plt
import os
filename_list =["NaiveBayes","J48","zeroR","RandomForest","DecisionStump","RandomTree","REPTree","DecisionTable","multiple"]
s1="rootMSE"
s2="precision"
#s is a choose
s=s2
x=[]


# ay = fig.add_subplot(111)
# ax.plot(x2, label='9-randomforest')
# plt.plot(x3, label='9-j48')
y=[]
for k in range(3,50,3):
    y.append(k)
file_dir = 'C:/Users/25755/Desktop/1级队列/数据集'
    #file_dir = 'C:/Users/25755/Desktop/已训练过的big_data'
path = os.listdir(file_dir)
data_name=[]
for i in range(0, 30):
    data_name.append(path[i])

for j in range(30):
    fig = plt.figure(facecolor='white')
    for i in range(8):
        f = open('D:/project/java/leetcode/out/' + filename_list[i] + s + '_' + data_name[j] + '_.txt', 'r')
        x = []
        for line in f:
            x.append(float(line[:-1]))
        ax = fig.add_subplot(111)
        ax.plot(y, x, label=filename_list[i])
    plt.plot()
    plt.xlabel("num of classfier")
    plt.ylabel(s)
    plt.legend()
    plt.savefig('C:/Users/25755/Desktop/机器学习作业/数据图/分类器数量不同/' + "dataset_"+str(j) + '_' + s + '_.png')
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

