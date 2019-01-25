package com.school.backend.service;

import com.school.backend.entities.Marks;
import com.school.backend.entities.Results;
import com.school.backend.entities.Student;
import com.school.backend.entities.Subject;
import com.school.backend.exceptions.GenericNotFoundException;
import com.school.backend.model.StudentData;
import com.school.backend.repository.MarksRepository;
import com.school.backend.repository.ResultsRepository;
import com.school.backend.repository.StudentRepository;
import com.school.backend.repository.SubjectRepository;
import com.school.backend.util.EntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ResultsRepository resultsRepository;

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public StudentData saveOrUpdateResultsForStudent(Long studentId, Results results){

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        List<Marks> savedMarksList = new ArrayList<>();
        Results savedResults;

        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            String resultTerm = results.getTerm();
            String resultGrade = results.getGrade();

            List<Results> existingResults = student.getStudentResults();

            List<Results> termResultsList = existingResults.parallelStream().
                    filter(result -> result.getTerm().equals(resultTerm) && result.getGrade().equals(resultGrade)).
                    collect(Collectors.toList());

            if(termResultsList.size() > 0) {
                savedResults = termResultsList.get(0);
                List<Marks> termMarksList = savedResults.getMarks();

                List<Marks> currentMarks = results.getMarks();

                for (Marks mark : currentMarks) {
                    Marks savedMarks = getMarksFromMarksList(mark, termMarksList);
                    termMarksList = getRemainingMarks(savedMarks, termMarksList);
                    if(savedMarks != null){
                        savedMarks.setScore(mark.getScore());
                        savedMarksList.add(savedMarks);
                    }
                    else{
                        Optional<Subject> subjectById = subjectRepository.findById(mark.getSubject().getId());
                        subjectById.ifPresent(mark::setSubject);
                        results.setMarks(new ArrayList<>());
                        mark.setResults(savedResults);
                        savedMarksList.add(mark);
                    }

                    savedMarksList.addAll(termMarksList);
                }

                savedMarksList = marksRepository.saveAll(savedMarksList);
                savedResults.setMarks(savedMarksList);
                resultsRepository.save(savedResults);
            }
            else{
                List<Marks> currentMarks = results.getMarks();
                results.setMarks(new ArrayList<>());
                savedResults = resultsRepository.save(results);

                for(Marks mark: currentMarks){
                    Optional<Subject> subjectById = subjectRepository.findById(mark.getSubject().getId());
                    subjectById.ifPresent(mark::setSubject);
                    results.setMarks(new ArrayList<>());
                    mark.setResults(savedResults);
                    savedMarksList.add(mark);
                }

                savedMarksList = marksRepository.saveAll(savedMarksList);
                savedResults.setMarks(savedMarksList);
                savedResults = resultsRepository.save(savedResults);
                existingResults.add(savedResults);

            }

            student.setStudentResults(existingResults);

            Student savedStudent = studentRepository.save(student);
            return EntityToDtoMapper.studentDataFromStudentEntity(savedStudent);
        }
        else {
            throw new GenericNotFoundException("Could not save or update results.");
        }

    }

    private Marks getMarksFromMarksList(Marks mark, List<Marks> marksList){
        for(Marks testMark: marksList){
            if(mark.getSubject().getId().equals(testMark.getSubject().getId())){
                return testMark;
            }
        }

        return null;
    }

    private List<Marks> getRemainingMarks(Marks mark, List<Marks> marksList){
        marksList.remove(mark);
        return marksList;
    }

    public boolean saveStudentResults(){
        return true;
    }
}
