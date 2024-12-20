package com.rperez.lazycat.data

data class NekoImageResponse(
    var items: List<Item>,
    val count: Int
)

data class Item(
    val id: Int,
    val id_v2: String,
    val image_url: String,
    val sample_url: String,
    val image_size: Int,
    val image_width: Int,
    val image_height: Int,
    val sample_size: Int,
    val sample_width: Int,
    val sample_height: Int,
    val source: String,
    val source_id: Int,
    val rating: String,
    val verification: String,
    val hash_md5: String,
    val hash_perceptual: String,
    val color_dominant: List<Int>,
    val color_palette: List<List<Int>>,
    val duration: Int,
    val is_original: Boolean,
    val is_screenshot: Boolean,
    val is_flagged: Boolean,
    val is_animated: Boolean,
    val artist: Artist,
    val characters: List<Character>,
    val tags: List<Tag>,
    val created_at: Double,
    val updated_at: Double
)

data class Artist(
    val id: Int,
    val id_v2: String,
    val name: String,
    val aliases: List<String>,
    val image_url: String,
    val links: List<String>,
    val policy_repost: Boolean,
    val policy_credit: Boolean,
    val policy_ai: Boolean
)

data class Character(
    val id: Int,
    val id_v2: String,
    val name: String,
    val aliases: List<String>,
    val description: String,
    val ages: List<Int>,
    val height: Int,
    val weight: Int,
    val gender: String,
    val species: String,
    val birthday: String,
    val nationality: String,
    val occupations: List<String>
)

data class Tag(
    val id: Int,
    val id_v2: String,
    val name: String,
    val description: String,
    val sub: String,
    val is_nsfw: Boolean
)
