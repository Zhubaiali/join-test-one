package com.yizhi.student.service.impl;

import com.yizhi.student.dao.ClassDao;
import com.yizhi.student.dao.CollegeDao;
import com.yizhi.student.dao.MajorDao;
import com.yizhi.student.domain.ClassDO;
import com.yizhi.student.domain.CollegeDO;
import com.yizhi.student.domain.MajorDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.yizhi.student.dao.StudentInfoDao;
import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;



@Service
public class StudentInfoServiceImpl implements StudentInfoService {
	@Autowired
	private ClassDao classDao;

	@Autowired
	private CollegeDao collegeDao;

	@Autowired
	private MajorDao majorDao;
	@Autowired
	private StudentInfoDao studentInfoDao;
	
	@Override
	public StudentInfoDO get(Integer id){
		System.out.println("======service层中传递过来的id参数是：" + id + "======");
		return studentInfoDao.get(id);
	}


	@Override
	public List<StudentInfoDO> list(Map<String, Object> map){
		return studentInfoDao.list(map);
	}

	//"===================================================================================="


	@Override
	public int count(Map<String, Object> map){
		return studentInfoDao.count(map);
	}

	@Override
	public int save(StudentInfoDO studentInfo){
		verify(studentInfo);

		studentInfo.setAddTime(new Date());
		studentInfo.setAddUserid(studentInfo.getAddUserid());
		return studentInfoDao.save(studentInfo);
	}

	@Override
	public int update(StudentInfoDO studentInfo){
		verify(studentInfo);

		studentInfo.setEditTime(new Date());
		return studentInfoDao.update(studentInfo);
	}
	
	@Override
	public int remove(Integer id){
		return studentInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		List<Integer> idList = studentInfoDao.selectList();
		System.out.println("idList:  " + idList);
		boolean falg = false;
		for (int i = 0; i < ids.length; i++) {
			falg = false;
			for (int j = 0; j < idList.size(); j++) {
				if (ids[i].equals(idList.get(j))) {
					falg = true;
					break;
				}
			}
			if (!falg) {
				throw new RuntimeException();
			}
		}
		return studentInfoDao.batchRemove(ids);
	}
	private void isValid(Integer id, List<Integer> ids) {
		boolean flag = true;
		for (Integer i : ids) {
			flag = false;
			if (i.equals(id)) {
				flag = true;
				break;
			}
		}
		if (flag == false) {
			throw new RuntimeException();
		}
	}

	//提取校验方法
	private void verify(StudentInfoDO studentInfo) {
		List<Integer> classes = classDao.getIds();
		List<Integer> colleges = collegeDao.getIds();
		List<Integer> majors = majorDao.getIds();

		Integer classId = studentInfo.getClassId();
		Integer collegeId = studentInfo.getTocollege();
		Integer tomajorId = studentInfo.getTomajor();
		isValid( classId,classes);
		isValid( collegeId,colleges);
		isValid( tomajorId,majors);
	}
}
