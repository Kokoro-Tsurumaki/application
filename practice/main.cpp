#include <chrono>
#include <iostream>
#include <random>
#include <cmath>
int main() {
    std::wcout.imbue(std::locale(""));
    system("chcp 65001");
    int inputNumber,grade,maxNumber,inputCount;
    // 选择难度
    do {
        std::cout << "请选择难度 · 1 简单  · 2 一般  · 3 困难  · 4 极难 :" << std::endl ;
        std::cin >> grade;
        if (grade >= 1 && grade <= 4) {
            maxNumber = 10 * pow(10, grade);
            inputCount = grade * 10;
            break;
        }
        std::cout << "必须是1,2,3,4的一个" << std::endl;
    } while (true);

    // 创建随机数引擎 时间作为种子
    unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();
    std::default_random_engine generator(seed);
    // 创建分布对象
    std::uniform_int_distribution<int> distribution(1, maxNumber); // 1到100之间的整数
    // 生成随机数并输出
    int randomNumber = distribution(generator);

    do {
        std::cout << "你有"<< inputCount <<"次机会,请输入 1 - "<< maxNumber <<" 之间的数字：" ;
        std::cin >> inputNumber;
        std::cout << "You entered " << inputNumber << '\n';
        if (inputNumber > randomNumber) {
            std::cout << "猜大了" << std::endl;
        }else if (inputNumber < randomNumber) {
            std::cout << "猜小了" << std::endl;
        }else {
            std::cout << "猜对了" << std::endl;
            break;
        }
        inputCount --;
        if (inputNumber <= 0) {
            std::cout << "你的次数为0,失败了" << std::endl;
            break;
        }
    } while (inputNumber != randomNumber);
    return 0;
}