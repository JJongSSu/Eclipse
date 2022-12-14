package iducs.java.pim.service;

import iducs.java.pim.model.Member;
import iducs.java.pim.repository.MemberRepository;
import iducs.java.pim.repository.MemberRepositoryImpl;
import iducs.java.pim.view.MemberView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MemberServiceImpl<T> implements MemberService<T> {

    MemberView memberView = new MemberView();

    MemberRepository<T> memberRepository = null;
    private String memberdb = null;

    public MemberServiceImpl(String memberDB) {
        memberRepository = new MemberRepositoryImpl<T>();
        this.memberdb = memberDB;
    }

    @Override
    public T login(String email, String pw) {
        T member = (T) new Member();
        ((Member) member).setEmail(email);
        ((Member) member).setPw(pw);
        T retMember = memberRepository.readByEmail(member);
        if(retMember != null)
            return retMember;
        else
            return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public int postMember(T member) {
        int ret = 0;
        if(memberRepository.create(member) > 0)
            ret = 1;
        return ret;
    }

    @Override
    public T getMember(T member) {
        return null;
    }

    @Override
    public List<T> getMemberList() {
        //return memberRepository.getMemberList();
        return memberRepository.readList();
    }

    @Override
    public int putMember(T member) {
        return 0;
    }

    @Override
    public int deleteMember(T member) {
        return 0;
    }

    @Override
    public List<T> findMemberByPhone(T member) {
        return null;
    }

    @Override
    public List<T> sortByName(String order) {
        return null;
    }

    @Override
    public List<T> paginateByPerPage(int pageNo, int perPage) {
        return null;
    }

    @Override
    public void readFile() {
        File file = new File(memberdb); // ?????? ????????? ??????
        if (file.canRead()) { // ??????????????? ?????? ??? ?????? ??????
            try {
                MemberFileReader<Member> mfr = new MemberFileReader<Member>(file);
                memberRepository.setMemberList((List<T>) mfr.readMember());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else { // ?????? ??? ?????? ??????
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveFile() {
        // member list??? ????????? ??????
        File file = new File(memberdb);
        try {
            MemberFileWriter<Member> mfw = new MemberFileWriter<Member>(file);
            mfw.saveMember((List<Member>) memberRepository.readList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyUpdate() { // ?????? ??? ????????? ????????? ????????? ??????, ?????? ?????? ?????? ????????? ?????????
        saveFile();
        readFile();
    }
}
