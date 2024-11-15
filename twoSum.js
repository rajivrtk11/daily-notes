var twoSum = function(nums, target) {
    const map = {};

    nums.forEach((ele, i) => {
        const findingKey = target - ele;
        if(map[findingKey] != undefined) {
            return [map[findingKey], i];
        }

        map[ele] = i;
    })

    return []
};
