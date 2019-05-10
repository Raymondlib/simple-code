from sklearn import datasets as ds
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.neural_network import MLPClassifier
from sklearn.ensemble import BaggingClassifier
from sklearn.preprocessing import LabelEncoder
import numpy as np
import pandas as pd
import pickle
import warnings

def code(data,add):
	for i in range(add,data.shape[1]-1):
		if type(data[i][0])==str:
			newD = pd.get_dummies(data[i])
			idx = list(data.columns)
			idx.remove(i)
			data = pd.concat([data[idx],newD],axis=1)
			i += newD.shape[1]-1
	return data

def load_dataset(filename):
	all_data = pd.read_csv(filename,header=None,sep=',',error_bad_lines=False)
	# all_data = all_data.drop([0],axis=1)
	class_label = LabelEncoder()
	all_data[all_data.shape[1]-1] = class_label.fit_transform(all_data[all_data.shape[1]-1].values)
	all_data = code(all_data,0)
	x = np.array(all_data.iloc[:,0:all_data.shape[1]-1]).astype('float')
	y = np.array(all_data.iloc[:,all_data.shape[1]-1]).astype('float').T
	return x,y

def load_openml():
	data = ds.fetch_openml('vehicle')
	return data.data,data.target,'dataset\\vehicle'

def load_buildin():
	# data = ds.fetch_20newsgroups_vectorized()
	# data = ds.fetch_20newsgroups()
	# data = ds.fetch_covtype()
	# data = ds.fetch_kddcup99()
	# data = ds.fetch_lfw_pairs()
	# data = ds.fetch_lfw_people()
	# data = ds.fetch_olivetti_faces()
	# data = ds.fetch_rcv1()
	data = ds.load_digits()
	# data = ds.load_wine()
	return data.data,data.target,'dataset\\digits'

def getCScore(x,y,clf_name,n_es):
	times = 1
	if clf_name=='knn':
		clf = KNeighborsClassifier()
	elif clf_name=='dtree':
		clf = DecisionTreeClassifier()
	elif clf_name=='nbayes':
		clf = GaussianNB()
	elif clf_name=='nn':
		clf = MLPClassifier()
		times = 1
	ans = []
	for i in range(times):
		bagging = BaggingClassifier(base_estimator=clf,n_estimators=n_es,oob_score=True)
		bagging.fit(x,y)
		ans.append(bagging.oob_score_)
	return sum(ans)/len(ans)

def getALLScore(x,y):
	ans = []
	for clf_name in ['knn','dtree','nbayes','nn']:
		if clf_name=='knn':
			clf = KNeighborsClassifier()
		elif clf_name=='dtree':
			clf = DecisionTreeClassifier()
		elif clf_name=='nbayes':
			clf = GaussianNB()
		elif clf_name=='nn':
			clf = MLPClassifier()
		bagging = BaggingClassifier(base_estimator=clf,n_estimators=1,oob_score=True)
		bagging.fit(x,y)
		ans.append(bagging.oob_score_)
	return ans

if __name__ == '__main__':
	warnings.filterwarnings('ignore')
	filename = 'dataset\\adult.data'
	fs = filename.split('.')
	f_name = ''
	for idx in range(len(fs)-1):
		f_name += fs[idx]
	# x,y = load_dataset(filename)
	# x,y,f_name = load_buildin()
	x,y,f_name = load_openml()
	print('load data end.')
	#all_scores = []
	clf_names = ['knn','dtree','nbayes','nn']
	for clf_name in clf_names:
		scores = []
		for i in range(30,240,10):
			scores.append(getCScore(x,y,clf_name,i))
			print(clf_name,i)
		#all_scores.append(scores)
		pickle.dump(scores,open(f_name+'_'+clf_name+'_score.pkl','wb'))
	#print(len(all_scores))
	#print(all_scores)
	#pickle.dump(all_scores,open(filename.split('.')[0]+'_score.pkl','wb'))

	# simpleAns = getALLScore(x,y)
	# pickle.dump(simpleAns,open(f_name+'_simpleScore.pkl','wb'))